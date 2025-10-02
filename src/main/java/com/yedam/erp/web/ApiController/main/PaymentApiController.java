//package com.yedam.erp.web.ApiController.main;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yedam.erp.service.main.PaymentService;
//import com.yedam.erp.vo.main.PaymentConfirmRequestVO;
//import com.yedam.erp.vo.main.PaymentPrepareRequestVO;
//import com.yedam.erp.vo.main.PaymentPrepareResponseVO;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@RestController
//@RequestMapping("/api/payments")
//public class PaymentApiController {
//
//    private final PaymentService paymentService;
//
//    /**
//     * 결제 준비 API
//     * @param requestVO 프론트에서 전송한 구독 옵션 및 계산된 금액
//     * @param httpServletRequest 현재 요청 정보 (세션에서 사용자 ID를 얻기 위함)
//     * @return 성공 시 토스 위젯에 필요한 정보, 실패 시 에러 응답
//     */
//    @PostMapping("/prepare")
//    public ResponseEntity<?> preparePayment(@RequestBody PaymentPrepareRequestVO requestVO, HttpServletRequest httpServletRequest) {
//        try {
//            // [중요] 실제 운영 환경에서는 Spring Security의 SecurityContextHolder 또는 세션에서
//            // 현재 로그인된 사용자의 고유 ID(adminId)를 안전하게 가져와야 합니다.
//            // 예: String adminId = (String) httpServletRequest.getSession().getAttribute("loggedInAdminId");
//            // if (adminId == null) {
//            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"로그인이 필요합니다.\"}");
//            // }PaymentPrepareResponseDTO
//            String adminId = "temp_admin_user_01"; // <<-- 개발 및 테스트를 위한 임시 관리자 ID
//
//            PaymentPrepareResponseVO response = paymentService.preparePayment(requestVO, adminId);
//            return ResponseEntity.ok(response);
//            
//        } catch (IllegalArgumentException e) {
//            log.warn("결제 준비 중 데이터 검증 실패: {}", e.getMessage());
//            return ResponseEntity.badRequest().body("{\"status\":\"VALIDATION_FAIL\", \"message\":\"" + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            log.error("결제 준비 중 서버 오류 발생", e);
//            return ResponseEntity.internalServerError().body("{\"status\":\"ERROR\", \"message\":\"결제 준비 중 오류가 발생했습니다.\"}");
//        }
//    }
//
//    /**
//     * 결제 최종 승인 API
//     * @param requestDTO 토스 결제 성공 후 전달되는 결제 정보
//     * @return 성공 또는 실패 메시지PaymentConfirmRequestDTO
//     */
//    @PostMapping("/confirm")
//    public ResponseEntity<?> confirmPayment(@RequestBody PaymentConfirmRequestVO  requestDTO) {
//        try {
//            paymentService.confirmPayment(requestDTO);
//            return ResponseEntity.ok().body("{\"status\":\"SUCCESS\", \"message\":\"결제가 성공적으로 완료되었습니다.\"}");
//        } catch (Exception e) {
//            log.error("결제 승인 처리 중 오류 발생: orderId={}", requestDTO.getOrderId(), e);
//            return ResponseEntity.badRequest().body("{\"status\":\"FAIL\", \"message\":\"" + e.getMessage() + "\"}");
//        }
//    }
//}
//
//
////
////import java.util.Map;
////
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestBody;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////
////import com.yedam.erp.service.main.PaymentService;
////import com.yedam.erp.vo.main.PaymentPrepareRequestVO;
////import com.yedam.erp.vo.main.SubPayVO;
////
////import lombok.RequiredArgsConstructor;
////
////@RestController
////@RequestMapping("/api/payment")
////@RequiredArgsConstructor
////public class PaymentApiController {
////
////    private final PaymentService paymentService;
////
////    // 1. 결제 준비 API (기존과 동일)
////    @PostMapping("/prepare")
////    public ResponseEntity<PaymentPrepareRequestVO> preparePayment(@RequestBody PaymentPrepareRequestDto requestDto) {
////    	PaymentPrepareRequestVO responseDto = paymentService.preparePayment(requestDto);
////        return ResponseEntity.ok(responseDto);
////    }
////    
////    // 2. 최종 결제 승인 및 저장 API (새로 추가)
////    @PostMapping("/confirm")
////    public ResponseEntity<SubPayVO> confirmPayment(
////            @RequestBody Map<String, Object> payload) {
////        
////        String paymentKey = (String) payload.get("paymentKey");
////        String orderId = (String) payload.get("orderId");
////        Long amount = Long.parseLong(String.valueOf(payload.get("amount")));
////
////        try {
////            SubPayVO finalPaymentInfo = paymentService.confirmPayment(paymentKey, orderId, amount);
////            return ResponseEntity.ok(finalPaymentInfo);
////        } catch (Exception e) {
////            // 실패 시 에러 응답 반환
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
////        }
////    }
////}