package com.yedam.erp.service.impl.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import com.yedam.erp.mapper.main.SubLogMapper;
import com.yedam.erp.mapper.main.SubPayMapper;
import com.yedam.erp.mapper.main.SubscriptionMapper;
import com.yedam.erp.service.main.SubscriptionService;
import com.yedam.erp.service.main.TossPaymentsService;
import com.yedam.erp.vo.main.PayLogVO;
import com.yedam.erp.vo.main.SubLogVO;
import com.yedam.erp.vo.main.SubPayVO;
import com.yedam.erp.vo.main.SubscriptionVO;
import com.yedam.erp.vo.main.TossConfirmResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Transactional
@Service("subscriptionService")
@RequiredArgsConstructor
@Slf4j
public class SubsciptionServiceImpl implements SubscriptionService {
    @Value("${toss.biling.secretKey}")
    private String bilingSecretKey;
    private final SubscriptionMapper subscriptionMapper;
    private final SubPayMapper subPayMapper; 
    private final SubLogMapper subLogMapper; 
    private final TossPaymentsService tossPaymentsService;
    
    // 결제 준비 데이터를 임시 저장하는 캐시 
    private final Map<String, Map<String, Object>> preparedPaymentsCache = new ConcurrentHashMap<>();

    /**
     * COM001, COM002 형식의 다음 회사 코드를 생성합니다.
     */
    private String generateComCode() {
        String maxCode = subscriptionMapper.getMaxComCode(); 
        int nextNum = 1;

        if (maxCode != null && maxCode.startsWith("COM")) {
            try {
                nextNum = Integer.parseInt(maxCode.substring(3)) + 1;
            } catch (NumberFormatException ignored) {}
        }
        return String.format("COM%03d", nextNum);
    }
    
    public Map<String, Object> getCompanyInfo(Long matNo) {
        return subscriptionMapper.selectCompanyInfoByMatNo(matNo);
    }

    /**
     * [STEP 4-1] 결제 준비 (FE 요청 시 Order ID 생성 및 캐시 저장)
     */
    public Map<String, Object> preparePayment(Map<String, Object> requestData, Long matNo) {
        
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 15).toUpperCase();
        Long finalAmount = ((Number) requestData.get("finalAmount")).longValue();
        
        requestData.put("orderId", orderId);
        requestData.put("matNo", matNo);
        preparedPaymentsCache.put(orderId, requestData);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", orderId);
        response.put("orderName", "NGR_ERP 플랜 구독 결제");
        response.put("amount", finalAmount);
        
        return response;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class) 
    public String completePaymentTransaction(String paymentKey, String orderId, Long amount) {

        Map<String, Object> preparedData = preparedPaymentsCache.get(orderId);
        if (preparedData == null) {
            log.error("결제 승인 실패: 유효하지 않은 orderId: {}", orderId);
            throw new RuntimeException("유효하지 않은 주문 정보입니다.");
        }

        // Toss Payments 서버 승인 요청
        Map<String, Object> tossResult;
        try {
            tossResult = tossPaymentsService.confirmPayment(paymentKey, orderId, amount);
            if (!"DONE".equals(tossResult.get("status"))) {
                String errorCode = (String) tossResult.getOrDefault("code", "TOSS_API_FAIL");
                String errorMessage = (String) tossResult.getOrDefault("message", "토스 결제 승인 실패");
                throw new RuntimeException("결제 승인 실패: " + errorMessage + " (" + errorCode + ")");
            }
        } catch (IOException e) {
            log.error("Toss Payments 통신 오류 발생. OrderId: {}", orderId, e);
            throw new RuntimeException("Toss Payments 통신 오류", e);
        }

        // 안전한 Long 변환 헬퍼
        java.util.function.Function<Object, Long> toLong = obj -> {
            if (obj == null) return 0L;
            if (obj instanceof Number) return ((Number) obj).longValue();
            try {
                return Long.parseLong(String.valueOf(obj));
            } catch (NumberFormatException e) {
                throw new RuntimeException("숫자로 변환할 수 없는 값: " + obj, e);
            }
        };

        // 회사 코드 생성 및 VO 객체 구성
        Long matNo = toLong.apply(preparedData.get("matNo"));
        Long subPlanNo = toLong.apply(preparedData.get("subPlanNo"));
        String newComCode = generateComCode();
        String subCode = "SUB-" + UUID.randomUUID().toString().substring(0, 15).toUpperCase();

        // SubscriptionVO 구성
        SubscriptionVO subVo = new SubscriptionVO();
        subVo.setSubCode(subCode);
        subVo.setSubPlanNo(subPlanNo);
        subVo.setTotalUsers(toLong.apply(preparedData.get("userCount")));
        subVo.setMon(toLong.apply(preparedData.get("duration")));
        subVo.setTotalPay(amount);
        subVo.setMatNo(matNo);
        subVo.setComCode(newComCode);
        subVo.setSubStatus("ACTIVE");

        // SubPayVO 구성
        Long adjustmentCharge = toLong.apply(preparedData.get("adjustmentCharge"));
        Long excessFee = adjustmentCharge > 0 ? adjustmentCharge : 0L;
        Long reductionFee = adjustmentCharge < 0 ? Math.abs(adjustmentCharge) : 0L;

        String paymentType = (String) preparedData.getOrDefault("paymentType", "NORMAL");
        String pgIsAuto = "RECURRING".equals(paymentType) ? "Y" : "N";
        String billingKey = "RECURRING".equals(paymentType) ? (String) tossResult.get("billingKey") : null;

        SubPayVO payVo = new SubPayVO();
        payVo.setAmount(amount);
        payVo.setPgIsAuto(pgIsAuto);
        payVo.setTransactionNo(paymentKey);
        payVo.setPayMethod((String) tossResult.get("method"));
        payVo.setSubCode(subCode);
        payVo.setBillingKeyNo(billingKey);
        payVo.setExcessPee(excessFee);
        payVo.setReductionFee(reductionFee);

        // SubLogVO 구성
        SubLogVO subLogVo = new SubLogVO();
        subLogVo.setSubType("NEW");
        subLogVo.setNewSubPlan(subPlanNo);
        subLogVo.setChangePay(amount);
        subLogVo.setSubCode(subCode);

        // DB 트랜잭션 실행
        subscriptionMapper.insertSubscription(subVo);
        subPayMapper.insertPayLog(payVo);
        subLogMapper.insertSubLog(subLogVo);

        // Company 테이블 업데이트
        Map<String, Object> companyUpdateParams = new HashMap<>();
        companyUpdateParams.put("comCode", newComCode);
        companyUpdateParams.put("compName", preparedData.get("compName"));
        companyUpdateParams.put("brm", toLong.apply(preparedData.get("brm")));
        companyUpdateParams.put("ceo", preparedData.get("ceo"));
        companyUpdateParams.put("matNo", matNo);

        subscriptionMapper.updateCompanyComCode(companyUpdateParams);

        // 캐시 정리 및 comCode 반환
        preparedPaymentsCache.remove(orderId);
        return newComCode;
    }

    @Override
    public void insertSubscriptionLog(SubLogVO logVO) {
        subLogMapper.insertSubLog(logVO);
    }

	@Override
	public List<SubscriptionVO> findSubscriptionsByComCode(String comCode) {
		return subscriptionMapper.findSubscriptionsByComCode(comCode);
	}

	@Override
	public SubscriptionVO findLatestSubscriptionByMatNo(Long matNo) {
	    log.info("DB 조회를 위해 전달된 matNo: [{}]", matNo); // 로그 추가
	    return subscriptionMapper.findLatestSubscriptionByMatNo(matNo);
	}
	//빌링키 자동결제
	@Override
	public void saveBillingKey(String customerKey, String billingKey) {
		// customerKey와 billingKey를 매퍼에 전달하여 DB 업데이트
        int updatedRows = subscriptionMapper.updateBillingKeyByCustomerKey(customerKey, billingKey);

        if (updatedRows > 0) {
            log.info("DB 저장 성공 - customerKey: {}, billingKey: {}", customerKey, billingKey);
        } else {
            log.warn("빌링키를 업데이트할 구독 정보를 찾지 못했습니다. customerKey: {}", customerKey);
            // 필요하다면 예외를 발생시켜 롤백 처리
            throw new RuntimeException("Billing key update failed for customerKey: " + customerKey);
        }
	}

	@Override
	public SubscriptionVO createPendingSubscription(SubscriptionVO subscriptionVO) {
		subscriptionMapper.insertBiling(subscriptionVO);
	    // INSERT 후 생성된 subCode가 담긴 VO 객체를 반환
	    return subscriptionVO;
	}

	@Override
	public List<SubscriptionVO> findSubscriptionsDueForPaymentToday() {
		return subscriptionMapper.findSubscriptionsDueForPaymentToday();	
		}

	@Override
	public void processAutomaticPayment(SubscriptionVO subscription) {
		log.info("구독 코드 [{}]에 대한 자동 결제를 시작합니다.", subscription.getSubCode());

	    RestTemplate restTemplate = new RestTemplate();
	    String url = "https://api.tosspayments.com/v1/billing/" + subscription.getBillingKey();

	    // 1. 토스 API 호출 준비
	    HttpHeaders headers = new HttpHeaders();
	    String encodedSecretKey = Base64.getEncoder().encodeToString((bilingSecretKey + ":").getBytes(StandardCharsets.UTF_8));
	    headers.set("Authorization", "Basic " + encodedSecretKey);
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    Map<String, Object> body = new HashMap<>();
	    body.put("customerKey", subscription.getCustomerKey());
	    body.put("amount", subscription.getTotalPay());
	    body.put("orderId", "SUB-" + subscription.getSubCode() + "-" + System.currentTimeMillis());

	    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

	    try {
	        // 2. API 호출
	        ResponseEntity<TossConfirmResponseVO> response = restTemplate.postForEntity(url, requestEntity, TossConfirmResponseVO.class);

	        if (response.getStatusCode() == HttpStatus.OK && "DONE".equals(response.getBody().getStatus())) {
	            // 3. 결제 성공 시, 프로시저 호출
	            TossConfirmResponseVO tossResponse = response.getBody();
	            log.info("결제 성공! PaymentKey: {}", tossResponse.getPaymentKey());

	            PayLogVO logVO = new PayLogVO();
	            logVO.setAmount(tossResponse.getTotalAmount());
	            logVO.setTransactionNo(tossResponse.getPaymentKey());
	            logVO.setPayMethod(tossResponse.getMethod());
	            logVO.setSubCode(subscription.getSubCode());
	            logVO.setBillingKeyNo(subscription.getBillingKey());

	            subscriptionMapper.processPayment(logVO); // ✨ DB 프로시저 호출
	            log.info("구독 코드 [{}]의 DB 처리가 완료되었습니다.", subscription.getSubCode());
	        } else {
	            throw new RuntimeException("API 응답 오류: " + response.getBody());
	        }
	    } catch (Exception e) {
	        log.error("자동 결제 API 호출 중 예외 발생 - 구독 코드: {}", subscription.getSubCode(), e);
	        throw new RuntimeException("자동 결제 실패", e);
	    }		
	}

//    /**
//     * [STEP 4-3 & 4-4] Toss 승인 및 DB 트랜잭션 처리 (핵심)
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class) 
//    public String completePaymentTransaction(String paymentKey, String orderId, Long amount) {
//        
//        Map<String, Object> preparedData = preparedPaymentsCache.get(orderId);
//        if (preparedData == null) {
//            log.error("결제 승인 실패: 유효하지 않은 orderId: {}", orderId);
//            throw new RuntimeException("유효하지 않은 주문 정보입니다.");
//        }
//        
//        // ** 1. Toss Payments 서버에 최종 승인 요청 (보안 필수)**
//        Map<String, Object> tossResult;
//        try {
//            tossResult = tossPaymentsService.confirmPayment(paymentKey, orderId, amount);
//            if (!"DONE".equals(tossResult.get("status"))) {
//                String errorCode = (String) tossResult.getOrDefault("code", "TOSS_API_FAIL");
//                String errorMessage = (String) tossResult.getOrDefault("message", "토스 결제 승인 실패");
//                throw new RuntimeException("결제 승인 실패: " + errorMessage + " (" + errorCode + ")");
//            }
//        } catch (IOException e) {
//            log.error("Toss Payments 통신 오류 발생. OrderId: {}", orderId, e);
//            throw new RuntimeException("Toss Payments 통신 오류", e);
//        }
//        
//     // 2. 회사 코드 생성 및 VO 객체 구성
//        Long matNo = (Long) preparedData.get("matNo");
//
//        // 기존 문제 코드
//        // Long subPlanNo = ((Number) preparedData.get("subPlanNo")).longValue();
//
//        // 안전 변환 적용
//        Object subPlanObj = preparedData.get("subPlanNo");
//        Long subPlanNo = subPlanObj instanceof Number
//                ? ((Number) subPlanObj).longValue()
//                : Long.parseLong(String.valueOf(subPlanObj));
//
//        String newComCode = generateComCode(); 
//        String subCode = "SUB-" + UUID.randomUUID().toString().substring(0, 15).toUpperCase(); 
//        
//        // 2-1. SubscriptionVO 구성
//        SubscriptionVO subVo = new SubscriptionVO();
//        subVo.setSubCode(subCode);
//        subVo.setSubPlanNo(subPlanNo);
//        subVo.setTotalUsers(((Number) preparedData.get("userCount")).longValue());
//        subVo.setMon(((Number) preparedData.get("duration")).longValue());
//        subVo.setTotalPay(amount); 
//        subVo.setMatNo(matNo);
//        subVo.setComCode(newComCode);
//        subVo.setSubStatus("ACTIVE"); 
//        
//        // 2-2. SubPayVO 구성
//        Long adjustmentCharge = ((Number) preparedData.get("adjustmentCharge")).longValue();
//        Long excessFee = adjustmentCharge > 0 ? adjustmentCharge : 0L;
//        Long reductionFee = adjustmentCharge < 0 ? Math.abs(adjustmentCharge) : 0L;
//        
//        String paymentType = (String) preparedData.getOrDefault("paymentType", "NORMAL"); 
//        String pgIsAuto = "RECURRING".equals(paymentType) ? "Y" : "N";
//        String billingKey = "RECURRING".equals(paymentType) ? (String) tossResult.get("billingKey") : null; 
//        
//        SubPayVO payVo = new SubPayVO();
//        payVo.setAmount(amount);
//        payVo.setPgIsAuto(pgIsAuto);
//        payVo.setTransactionNo(paymentKey);
//        payVo.setPayMethod((String) tossResult.get("method")); 
//        payVo.setSubCode(subCode);
//        payVo.setBillingKeyNo(billingKey);
//        payVo.setExcessPee(excessFee);
//        payVo.setReductionPee(reductionFee);
//        
//        // 2-3. SubLogVO 구성 (NEW 타입)
//        SubLogVO subLogVo = new SubLogVO();
//        subLogVo.setSubType("NEW");
//        subLogVo.setNewSubPlan(subPlanNo);
//        subLogVo.setChangePay(amount);
//        subLogVo.setSubCode(subCode);
//        
//        // 3. DB 트랜잭션 실행 (4개 테이블 원자적 저장)
//        subscriptionMapper.insertSubscription(subVo);
//        subPayMapper.insertPayLog(payVo);
//        subLogMapper.insertSubLog(subLogVo);
//        
//        // 4. Company 테이블 업데이트 (COM_CODE 할당 및 기타 정보 업데이트)
//        Map<String, Object> companyUpdateParams = new HashMap<>();
//        companyUpdateParams.put("comCode", newComCode);
//        companyUpdateParams.put("compName", preparedData.get("compName"));
//        Object brmObj = preparedData.get("brm");
//        companyUpdateParams.put("brm", brmObj instanceof Number ? ((Number) brmObj).longValue() : Long.parseLong(String.valueOf(brmObj)));
//        companyUpdateParams.put("ceo", preparedData.get("ceo"));
//        companyUpdateParams.put("matNo", matNo);
//        
//        subscriptionMapper.updateCompanyComCode(companyUpdateParams);
//        
//        // 5. 캐시 정리 및 생성된 comCode 반환
//        preparedPaymentsCache.remove(orderId);
//        return newComCode;
//    }
}