package com.yedam.erp.web.ApiController.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yedam.erp.service.main.SubscriptionService;
import com.yedam.erp.vo.main.SubPlanVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping({"/sub", "/payment"})
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;
    
    private Long getLoggedInMatNo() {
        // [주의] 실제 구현 시 Spring Security Context에서 사용자 정보를 가져와야 합니다.
        return 1001L; 
    }

    /**
     * [GET] /sub/api/subDetail : Step 2 회사 정보 조회 API
     */
    @GetMapping("/api/subDetail")
    @ResponseBody
    public Map<String, Object> getSubDetail(@RequestParam(required = false) Long planId) {
        Long matNo = getLoggedInMatNo();
        Map<String, Object> companyInfo = subscriptionService.getCompanyInfo(matNo);
        
        Map<String, Object> result = new HashMap<>();
        result.put("company", companyInfo);
        
        return result;
    }


    /**
     * [POST] /payment/api/payment/prepare : [STEP 4-1] 결제 준비 API
     */
    @PostMapping("/api/payment/prepare")
    @ResponseBody
    public Map<String, Object> preparePayment(@RequestBody Map<String, Object> requestData) {
        Long matNo = getLoggedInMatNo();
        
        Map<String, Object> paymentInfo = subscriptionService.preparePayment(requestData, matNo);
        
        return paymentInfo;
    }

    /**
     * [GET] /payment/success : [STEP 4-3] Toss Payments 결제 성공 콜백
     */
    @GetMapping("/success")
    public ModelAndView paymentSuccess(
        @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount) {
        
        try {
            // 1. 서비스 호출: 서버 간 승인 및 DB 트랜잭션 실행
            String comCode = subscriptionService.completePaymentTransaction(paymentKey, orderId, amount);
            
            // 2. 성공 시 Step 5로 이동
            ModelAndView mav = new ModelAndView("redirect:/payment/complete");
            mav.addObject("comCode", comCode); 
            
            return mav;
            
        } catch (Exception e) {
            log.error("결제 승인 및 DB 트랜잭션 오류. OrderId: {}", orderId, e);
            // 오류 발생 시 결제 실패 페이지로 리다이렉트
            return new ModelAndView("redirect:/payment/fail?code=DB_TRANSACTION_FAILED&message=" + e.getMessage());
        }
    }

    /**
     * [GET] /payment/complete : Step 5 최종 완료 페이지
     */
    @GetMapping("/complete")
    public String paymentComplete(@RequestParam(required = false) String comCode, Model model) {
        model.addAttribute("comCode", comCode);
        model.addAttribute("targetStep", 5); 
        model.addAttribute("subPlan",new SubPlanVO());
        
        return "main/subDetail"; 
    }
    
    /**
     * [GET] /payment/fail : 결제 실패 후 호출되는 Fail URL 핸들러
     */
    @GetMapping("/fail")
    public ModelAndView paymentFail(
        @RequestParam(required = false) String code,
        @RequestParam(required = false) String message,
        @RequestParam(required = false) String orderId) {

        ModelAndView mav = new ModelAndView("main/fail"); // fail.html 경로
        mav.addObject("errorCode", code);
        mav.addObject("errorMessage", message);
        mav.addObject("orderId", orderId);
        return mav;
    }
}