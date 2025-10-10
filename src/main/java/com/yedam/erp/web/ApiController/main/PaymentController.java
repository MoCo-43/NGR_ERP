//package com.yedam.erp.web.ApiController.main;
//
//import com.yedam.erp.service.main.SubscriptionService;
//import com.yedam.erp.mapper.main.SubPayMapper; // SubPayMapper 추가
//import com.yedam.erp.vo.main.SubscriptionVO;
//import com.yedam.erp.vo.main.SubPayVO;
//import com.yedam.erp.vo.main.CompanyVO; 
//import com.yedam.erp.vo.main.SubLogVO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Map;
//import java.util.UUID;
//import java.time.LocalDate;
//import java.time.ZoneId;
//
//@Controller
//@RequestMapping("/payment")
//public class PaymentController {
//
//    private final SubscriptionService subService;
//    private final SubPayMapper subPayMapper; // SubPayMapper 주입
//    private final RestTemplate restTemplate;
//
//    @Value("${toss.secret-key}")
//    private String tossSecretKey;
//    
//    private static final double VAT_RATE = 0.1;
//
//    // SubPayMapper 주입을 포함하도록 생성자 변경
//    public PaymentController(SubscriptionService subService, SubPayMapper subPayMapper) {
//        this.subService = subService;
//        this.subPayMapper = subPayMapper; 
//        this.restTemplate = new RestTemplate();
//    }
//    
//    // =========================================================================
//    // 1. 결제 준비 (Client -> Server) - DB에 PENDING 상태로 임시 저장
//    // =========================================================================
//    /**
//     * 클라이언트 결제 정보를 DB(pay_log)에 PENDING 상태로 저장합니다.
//     * HttpSession 파라미터가 제거되었습니다.
//     */
//    @PostMapping("/prepare")
//    @ResponseBody
//    public Map<String, Object> preparePayment(@RequestBody Map<String, Object> requestData, CompanyVO currentUser) {
//        
//        String orderId = UUID.randomUUID().toString().substring(0, 15);
//        Long finalAmount = ((Number) requestData.get("finalAmount")).longValue();
//        
//        // 1. DB 저장을 위한 SubPayVO 생성 및 매핑 (임시 데이터)
//        SubPayVO tempPayVO = new SubPayVO();
//        tempPayVO.setOrderId(orderId);
//        tempPayVO.setAmount(finalAmount);
//        tempPayVO.setSubPlanNo(((Number) requestData.get("subPlanNo")).longValue());
//        
//        // currentUser에서 matNo를 가져옵니다. (인증 시스템에서 제공된다고 가정)
//        Long matNo = currentUser.getMatNo(); 
//        if (matNo == null) {
//            // matNo가 null인 경우의 에러 처리 로직 추가 필요
//            // 여기서는 0으로 임시 처리하거나 예외를 발생시킬 수 있습니다.
//            System.err.println("Error: Current user matNo is missing.");
//            // throw new RuntimeException("Authentication error: matNo required."); 
//            matNo = 0L;
//        }
//        tempPayVO.setMatNo(matNo); 
//        
//        // 기타 결제 정보 (Client에서 넘어온 데이터 사용)
//        tempPayVO.setExcessFee(((Number) requestData.getOrDefault("excessFee", 0)).longValue());
//        tempPayVO.setReductionFee(((Number) requestData.getOrDefault("reductionFee", 0)).longValue());
//
//        // 2. DB에 PENDING 상태로 임시 저장
//        subPayMapper.insertPayLogPending(tempPayVO);
//        
//        // 3. 클라이언트에 orderId, amount 반환
//        return Map.of(
//            "orderId", orderId,
//            "orderName", "NGR_ERP 구독 결제",
//            "amount", finalAmount
//        );
//    }
//
//    // =========================================================================
//    // 2. 결제 승인 및 DB 트랜잭션 (Toss Callback -> Server)
//    // =========================================================================
//    /**
//     * 토스페이먼츠 결제 성공 시 콜백 URL로 호출되며, 결제를 승인하고 DB에 최종 저장합니다.
//     * HttpSession 파라미터가 제거되었습니다.
//     */
//    @GetMapping("/success")
//    public String tossPaymentSuccess(
//            @RequestParam String paymentKey, 
//            @RequestParam String orderId, 
//            @RequestParam Long amount,
//            Model model) {
//        
//        // 1. DB에서 orderId로 PENDING 상태의 임시 구독 정보 조회
//        SubPayVO pendingPayVO = subPayMapper.selectPayLogPendingByOrderId(orderId);
//        
//        if (pendingPayVO == null) {
//            // DB에 데이터가 없거나 이미 처리되었거나 만료된 경우
//            return "redirect:/payment/fail?code=DATA_NOT_FOUND&message=결제 정보가 유효하지 않거나 이미 처리되었습니다.";
//        }
//        
//        // 2. 토스페이먼츠 결제 승인 요청
//        Map<String, Object> paymentData = callTossPaymentConfirm(paymentKey, orderId, amount);
//
//        // 3. 결제 승인 결과 확인
//        if (paymentData != null && paymentData.get("status").equals("DONE")) {
//            
//            // 4. 최종 VO 객체 생성 및 값 매핑
//            // DB에서 조회한 pendingPayVO와 Toss 응답을 조합하여 최종 VO를 생성합니다.
//            SubscriptionVO subVO = createSubscriptionVO(pendingPayVO, paymentData, orderId);
//            SubPayVO finalPayVO = createFinalPayLogVO(pendingPayVO, paymentData); // 업데이트에 사용할 최종 SubPayVO
//            SubLogVO subLogVO = createSubLogVO(pendingPayVO, paymentData); 
//            
//            try {
//                // 5. 핵심 트랜잭션 호출 (Subscription, SubLog 삽입 및 SubPay 업데이트)
//                // Service에서 생성된 최종 subCode가 finalPayVO에 설정되어 updatePayLogDone이 호출되어야 합니다.
//                String newComCode = subService.completeSubscriptionTransaction(subVO, finalPayVO, subLogVO);
//
//                // 6. 모델에 데이터 추가
//                model.addAttribute("comCode", newComCode);
//                model.addAttribute("subVO", subVO); 
//                
//                return "redirect:/subdetail#step-5"; 
//                
//            } catch (Exception e) {
//                // DB 트랜잭션 실패 시: PG사에 취소 요청하는 로직 필요
//                System.err.println("DB Transaction Failed: " + e.getMessage());
//                // callTossPaymentCancel(paymentKey, "DB 트랜잭션 실패로 인한 취소"); 
//                return "redirect:/payment/fail?code=DB_ERROR&message=DB 저장 중 오류 발생. 고객센터에 문의하세요.";
//            }
//        } else {
//            // 토스 승인 실패 시 (PENDING 상태 유지 또는 실패 상태로 DB 업데이트 로직 필요)
//            String errorCode = (String) paymentData.getOrDefault("code", "UNKNOWN_TOSS_ERROR");
//            String errorMessage = (String) paymentData.getOrDefault("message", "토스페이먼츠 승인 실패");
//            return "redirect:/payment/fail?code=" + errorCode + "&message=" + errorMessage;
//        }
//    }
//
//    /**
//     * 토스페이먼츠 결제 승인 API를 호출합니다.
//     */
//    private Map<String, Object> callTossPaymentConfirm(String paymentKey, String orderId, Long amount) {
//        try {
//            URI uri = UriComponentsBuilder
//                    .fromUriString("https://api.tosspayments.com/v1/payments/confirm")
//                    .build()
//                    .toUri();
//
//            // Secret Key를 Base64로 인코딩 (key:)
//            String encodedAuth = Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setBasicAuth(encodedAuth);
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            Map<String, Object> body = Map.of(
//                "paymentKey", paymentKey, 
//                "orderId", orderId, 
//                "amount", amount
//            );
//
//            ResponseEntity<Map> response = restTemplate.postForEntity(uri, body, Map.class);
//            
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                return response.getBody();
//            }
//            return response.getBody(); 
//
//        } catch (Exception e) {
//            System.err.println("Toss API Communication Error: " + e.getMessage());
//            return Map.of("code", "HTTP_ERROR", "message", "토스 서버 통신 오류");
//        }
//    }
//
//
//    // =========================================================================
//    // 4. VO 객체 생성 및 매핑 로직 (DB-조회 데이터 기준)
//    // =========================================================================
//
//    /**
//     * SubscriptionVO를 생성하고 매핑합니다.
//     * SubPayVO에서 가져온 데이터 (pendingPayVO)를 기반으로 합니다.
//     */
//    private SubscriptionVO createSubscriptionVO(SubPayVO payDataFromDB, Map<String, Object> tossPayData, String orderId) {
//        SubscriptionVO vo = new SubscriptionVO();
//        
//        // *주의*: duration, totalUsers 값은 SubPayVO에 임시 컬럼으로 저장했거나, 
//        // sub_plan_no로 조회한다고 가정하고 하드코딩된 값(6, 50)을 대체해야 합니다. 
//        int duration = 6; // 예시: 실제로는 DB에서 조회 필요
//        int totalUsers = 50; // 예시: 실제로는 DB에서 조회 필요
//        
//        // 1. 기간 계산 및 설정
//        Long totalAmount = payDataFromDB.getAmount(); // PENDING 저장 시의 최종 금액
//        Long totalPay = Math.round(totalAmount / (1 + VAT_RATE)); 
//        
//        LocalDate today = LocalDate.now();
//        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        Date endDate = Date.from(today.plusMonths(duration).minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
//        
//        // 2. VO에 값 설정
//        vo.setSubCode(orderId); // (Service에서 UUID로 최종 변경될 예정)
//        vo.setStartDate(startDate);
//        vo.setEndDate(endDate);
//        vo.setTotalUsers(totalUsers);
//        vo.setSubStatus("ACTIVE");
//        vo.setTotalPay(totalPay); 
//        vo.setMatNo(payDataFromDB.getMatNo());
//        vo.setMon(duration); 
//        vo.setSubPlanNo(payDataFromDB.getSubPlanNo());
//        
//        return vo;
//    }
//
//    /**
//     * SubPayVO (결제이력)의 최종 정보를 생성합니다. (PENDING 상태에 덮어쓸 데이터)
//     */
//    private SubPayVO createFinalPayLogVO(SubPayVO payDataFromDB, Map<String, Object> tossPayData) {
//        // DB에서 조회한 객체를 사용하여 업데이트할 필드만 설정
//        SubPayVO vo = new SubPayVO();
//        
//        // PENDING 데이터에서 orderId를 가져와 업데이트 조건으로 사용
//        vo.setOrderId(payDataFromDB.getOrderId()); 
//        
//        // 최종 데이터 설정
//        Long totalAmount = payDataFromDB.getAmount();
//        Long totalPay = Math.round(totalAmount / (1 + VAT_RATE));
//        Long vatAmt = totalAmount - totalPay;
//        
//        // 토스 응답에서 받은 PG사 정보를 업데이트
//        vo.setPgIsAuto("NORMAL"); // 정기결제 여부는 tossPayData에서 가져와야 함 (예: tossPayData.get("method")가 '카드(정기결제)'인 경우)
//        vo.setTransactionNo((String) tossPayData.get("paymentKey"));
//        vo.setPayMethod((String) tossPayData.get("method"));
//        vo.setBillingKeyNo((String) tossPayData.getOrDefault("billingKey", null));
//        vo.setVatAmt(vatAmt);
//        
//        // **subCode는 Service에서 최종 업데이트되어 updatePayLogDone이 호출되어야 합니다.**
//        
//        return vo;
//    }
//
//    /**
//     * SubLogVO (구독이력)를 생성하고 매핑합니다.
//     */
//    private SubLogVO createSubLogVO(SubPayVO payDataFromDB, Map<String, Object> tossPayData) {
//        SubLogVO vo = new SubLogVO();
//
//        int duration = 6; // 예시: 실제로는 DB에서 조회 필요
//        LocalDate today = LocalDate.now();
//        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        Date endDate = Date.from(today.plusMonths(duration).minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//        Long totalAmount = payDataFromDB.getAmount();
//        
//        vo.setSubType("NEW");
//        vo.setNewSubPlan(payDataFromDB.getSubPlanNo());
//        vo.setChangePay(totalAmount);
//        vo.setStartDate(startDate);
//        vo.setEndDate(endDate);
//        
//        return vo;
//    }
//}
