package com.yedam.erp.web.ApiController.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.main.CompanyService;
import com.yedam.erp.service.main.SubscriptionService;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.SubPlanVO;
import com.yedam.erp.vo.main.SubscriptionVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping({"/sub", "/payment"})
@RequiredArgsConstructor
@Log4j2
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;
    private final CompanyService companyService;
    private Long getLoggedInMatNo() {
        return SessionUtil.companyId();
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

        CompanyVO company = null;
        if (comCode != null && !comCode.isEmpty()) {
            // comCode를 사용해 DB에서 회사 정보를 조회합니다.
            company = companyService.getCompanyByComCode(comCode);
        }
        // 조회한 company 객체를 "company"라는 이름으로 모델에 추가합니다.
        model.addAttribute("company", company);

        model.addAttribute("comCode", comCode);
        model.addAttribute("targetStep", 5);
        model.addAttribute("subPlan", new SubPlanVO());

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
    // 특정 회사의 구독 내역을 조회하는 새로운 API
    @GetMapping("/api/subscriptions")
    @ResponseBody
    public List<SubscriptionVO> getSubscriptionsForCompany(@RequestParam String comCode) {
        return subscriptionService.findSubscriptionsByComCode(comCode);
    }
//    @GetMapping("/admin/subList/{matNo}")
//    // public String subList(@RequestParam(required = false) String comCode, Model model) { // 기존
//    public String subList(@RequestParam(required = false) Long matNo, Model model) { // Long matNo로 변경
//        log.info("======> 컨트롤러 실행됨. 전달받은 matNo: {}", matNo); // 로그 메시지 변경
//
//        SubscriptionVO subscription = null;
//        if (matNo != null) { // matNo null 체크
//            // Service에 findLatestSubscriptionByMatNo(Long matNo)가 있으므로 바로 호출
//            subscription = subscriptionService.findLatestSubscriptionByMatNo(matNo);
//            log.info("======> 조회된 구독 정보: {}", subscription);
//        } else {
//            log.warn("matNo가 전달되지 않았습니다. 기본 페이지를 표시합니다.");
//        }
//
//        model.addAttribute("subscription", subscription);
//        return "main/submanager";
//    }
    @GetMapping("/admin/subList/{matNo}")
    public String subList(@PathVariable Long matNo, Model model) {
        Long sessionMatNo = SessionUtil.companyId();
        log.info("PathVariable matNo: {}, Session matNo: {}", matNo, sessionMatNo);

        if (sessionMatNo == null) {
            log.error("세션에 matNo가 없습니다. 로그인이 필요합니다.");
            return "redirect:/login";
        }

        // PathVariable과 세션 matNo가 일치하는지 검증
        if (!matNo.equals(sessionMatNo)) {
            log.warn("비정상 접근: path matNo={}, session matNo={}", matNo, sessionMatNo);
            return "redirect:/access-denied";
        }
        //구독정보가져오는 부분
        SubscriptionVO subscription = subscriptionService.findLatestSubscriptionByMatNo(matNo);
        model.addAttribute("subscription", subscription);
        // 사용 가능한 모듈 리스트 생성 후 model에 추가
        if (subscription != null && subscription.getSubPlan() != null 
            && subscription.getSubPlan().getAvaiModules() != null
            && !subscription.getSubPlan().getAvaiModules().isEmpty()) {
            
            List<String> avaiModules = Arrays.asList(subscription.getSubPlan().getAvaiModules().split(","));
            model.addAttribute("avaiModules", avaiModules);
        }
        return "main/submanager";
    }
    
    @GetMapping("/contract-html")
    @ResponseBody
    public String getContractHtml() throws IOException {
        // src/main/resources/templates/main/content.html 읽어서 반환
        ClassPathResource resource = new ClassPathResource("templates/main/content.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
   }
