//package com.yedam.erp.service.main;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//import java.util.Base64;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.yedam.erp.config.TossPaymentsConfig; // 토스페이먼츠 키를 관리하는 설정 클래스
//import com.yedam.erp.mapper.PaymentMapper;    // DB 프로시저를 호출할 MyBatis 매퍼
//import com.yedam.erp.vo.main.PaymentPrepareRequestDto;
//import com.yedam.erp.vo.main.PaymentPrepareResponseDto;
//import com.yedam.erp.vo.main.SubPayVO;
//// import com.yedam.erp.repository.SubPlanRepository; // 플랜 정보 조회용
//// import com.yedam.erp.repository.TempOrderRepository; // 임시 주문 정보 저장/조회용
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    // private final SubPlanRepository subPlanRepository;
//    // private final TempOrderRepository tempOrderRepository;
//    private final PaymentMapper paymentMapper;
//    private final TossPaymentsConfig tossPaymentsConfig; // application.yml 등에서 키 값을 읽어옴
//
//    /**
//     * 1. 결제 준비
//     */
//    public PaymentPrepareResponseDto preparePayment(PaymentPrepareRequestDto requestDto) {
//        
//        // 1. DB에서 원본 데이터 조회하여 금액 검증 (보안상 필수)
//        // SubPlanVO plan = subPlanRepository.findById(requestDto.getSubPlanNo());
//        // long serverCalculatedAmount = calculateAmountOnServer(...); // 서버에서 금액 재계산
//        // if (requestDto.getFinalAmount() != serverCalculatedAmount) {
//        //     throw new IllegalArgumentException("결제 금액 불일치");
//        // }
//
//        // 2. 고유한 주문번호(orderId) 생성
//        String orderId = UUID.randomUUID().toString();
//        String orderName = "구독 상품 결제"; // plan.getPlanName() 등으로 실제 상품명 설정
//        
//        // 3. 검증된 주문 정보를 DB 임시 테이블이나 Redis에 저장
//        // TempOrder tempOrder = new TempOrder(orderId, requestDto, serverCalculatedAmount);
//        // tempOrderRepository.save(tempOrder);
//        
//        // 4. 클라이언트에 주문번호, 주문명, 검증된 금액 반환
//        return new PaymentPrepareResponseDto(orderId, orderName, requestDto.getFinalAmount());
//    }
//
//    /**
//     * 2. 최종 결제 승인
//     */
//    public SubPayVO confirmPayment(String paymentKey, String orderId, Long amount) throws Exception {
//        
//        // 1. 임시 저장된 주문 정보와 금액 일치 여부 검증
//        // TempOrder tempOrder = tempOrderRepository.findByOrderId(orderId);
//        // if (!tempOrder.getAmount().equals(amount)) {
//        //     throw new Exception("결제 금액 위변조 의심");
//        // }
//
//        // 2. 토스페이먼츠에 '결제 승인' API 요청
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = createTossApiHeaders();
//        Map<String, Object> body = new HashMap<>();
//        body.put("orderId", orderId);
//        body.put("amount", amount);
//
//        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
//        
//        // 토스페이먼츠의 응답을 받을 DTO (별도 생성 필요)
//        // TossPaymentResponse tossResponse;
//        try {
//            // tossResponse = restTemplate.postForObject(
//            //     TossPaymentsConfig.URL + paymentKey, requestEntity, TossPaymentResponse.class);
//        } catch (Exception e) {
//            // 토스 승인 실패 시 예외 처리
//            throw new Exception("토스페이먼츠 결제 승인에 실패했습니다.");
//        }
//        
//        // 3. DB 프로시저 호출을 위한 파라미터 준비
//        Map<String, Object> params = new HashMap<>();
//        // params.put("p_com_code", tempOrder.getComCode());
//        // params.put("p_sub_plan_no", tempOrder.getSubPlanNo());
//        // ... (프로시저에 필요한 모든 파라미터 설정) ...
//        // params.put("p_transaction_no", tossResponse.getPaymentKey());
//        // params.put("p_pay_method", tossResponse.getMethod());
//        // ... (excess_pee, reduction_pee 등 tempOrder에서 가져와 설정)
//        
//        // 4. MyBatis 매퍼를 통해 DB 프로시저 호출
//        // paymentMapper.createSubscription(params);
//        
//        // 5. 임시 주문 정보 삭제
//        // tempOrderRepository.delete(tempOrder);
//        
//        // 6. 최종 저장된 결제 정보를 조회하여 반환
//        // return paymentMapper.getPayLogByOrderId(orderId);
//
//        // ----- 개발 단계에서는 아래처럼 임시 객체를 반환하여 테스트할 수 있습니다. -----
//        SubPayVO tempResult = new SubPayVO();
//        tempResult.setAmount(amount);
//        tempResult.setTransactionNo(paymentKey);
//        tempResult.setPayDate(LocalDate.now());
//        return tempResult;
//    }
//
//    // 토스페이먼츠 API 요청 헤더 생성 헬퍼 메서드
//    private HttpHeaders createTossApiHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        
//        String secretKey = tossPaymentsConfig.getSecretKey();
//        Base64.Encoder encoder = Base64.getEncoder();
//        byte[] encodedBytes = encoder.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
//        headers.set("Authorization", "Basic " + new String(encodedBytes));
//        
//        return headers;
//    }
//}