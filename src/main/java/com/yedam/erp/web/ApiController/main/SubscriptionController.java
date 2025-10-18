package com.yedam.erp.web.ApiController.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.main.CompanyService;
import com.yedam.erp.service.main.EmpLoginService;
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
    private final EmpLoginService empLoginService;
    private Long getLoggedInMatNo() {
        return SessionUtil.companyId();
    }
    //정기결제 빌링키 발급 필요
    @Value("${toss.biling.secretKey}")
    private String tossBilingSecretKey;
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
//    @GetMapping("/admin/subList/{matNo}")
//    public String subLists(@PathVariable Long matNo, Model model) {
//        Long sessionMatNo = SessionUtil.companyId();
//        log.info("PathVariable matNo: {}, Session matNo: {}", matNo, sessionMatNo);
//
//        if (sessionMatNo == null) {
//            log.error("세션에 matNo가 없습니다. 로그인이 필요합니다.");
//            return "redirect:/login";
//        }
//
//        // PathVariable과 세션 matNo가 일치하는지 검증
//        if (!matNo.equals(sessionMatNo)) {
//            log.warn("비정상 접근: path matNo={}, session matNo={}", matNo, sessionMatNo);
//            return "redirect:/access-denied";
//        }
//        //구독정보가져오는 부분
//        SubscriptionVO subscription = subscriptionService.findLatestSubscriptionByMatNo(matNo);
//        model.addAttribute("subscription", subscription);
//        // 사용 가능한 모듈 리스트 생성 후 model에 추가
//        if (subscription != null && subscription.getSubPlan() != null 
//            && subscription.getSubPlan().getAvaiModules() != null
//            && !subscription.getSubPlan().getAvaiModules().isEmpty()) {
//            
//            List<String> avaiModules = Arrays.asList(subscription.getSubPlan().getAvaiModules().split(","));
//            model.addAttribute("avaiModules", avaiModules);
//        }
//        return "main/submanager";
//    }

    @GetMapping("/admin/subList")
    public String subListsBySession(Model model) {
        // 세션에서 회사 ID 가져오기
        Long sessionMatNo = SessionUtil.companyId();
        log.info("Session matNo: {}", sessionMatNo);

        if (sessionMatNo == null) {
            log.error("세션에 matNo가 없습니다. 로그인이 필요합니다.");
            return "redirect:/login";
        }

        // 구독 정보 가져오기
        SubscriptionVO subscription = subscriptionService.findLatestSubscriptionByMatNo(sessionMatNo);
        model.addAttribute("subscription", subscription);

        // 사용 가능한 모듈 리스트 생성 후 model에 추가
        if (subscription != null && subscription.getSubPlan() != null
                && subscription.getSubPlan().getAvaiModules() != null
                && !subscription.getSubPlan().getAvaiModules().isEmpty()) {
            
            List<String> avaiModules = Arrays.asList(subscription.getSubPlan().getAvaiModules().split(","));
            model.addAttribute("avaiModules", avaiModules);
        }

        return "main/submanager"; // 화면 이름
    }   
    
    /*
     * 정기결제 카드 등록 성공 시 콜백 -> 첫결제 실행 함
     * */
    @GetMapping("/billing-success")
    public ModelAndView handleBillingSuccess(@RequestParam String customerKey, @RequestParam("authKey") String authKey, Model model) { // ★ 반환타입 ModelAndView로 변경
        try {
            // 1. 토스페이먼츠로부터 빌링키 발급
            String billingKey = issueBillingKey(authKey, customerKey);

            // 2. DB의 'PENDING' 구독 정보를 찾아 '첫 결제'를 실행하고 'ACTIVE'로 전환
            //    (기존 saveBillingKey 대신 processFirstRecurringPayment 호출)
            String comCode = subscriptionService.processFirstRecurringPayment(customerKey, billingKey);

            log.info("정기결제 카드 등록 및 '첫 결제' 성공! comCode: {}", comCode);
            
            // 3.  공통 결제 완료 페이지(Step 5)로 리다이렉트
            //    (기존 "main/success" 뷰 반환 대신 리다이렉트)
            ModelAndView mav = new ModelAndView("redirect:/payment/complete");
            mav.addObject("comCode", comCode); 
            return mav;

        } catch (Exception e) {
            log.error("빌링키 발급 또는 첫 결제 처리 중 오류 발생. customerKey: {}", customerKey, e);
            
            // 4.  공통 실패 페이지로 리다이렉트
            //    (기존 "main/fail" 뷰 반환 대신 리다이렉트)
            ModelAndView mav = new ModelAndView("redirect:/payment/fail");
            mav.addObject("errorCode", "BILLING_SETUP_FAILED");
            mav.addObject("errorMessage", "카드 등록 및 첫 결제 처리 중 오류가 발생했습니다: " + e.getMessage());
            return mav;
        }
    }
//    @GetMapping("/billing-success")
//    public String handleBillingSuccess(@RequestParam String customerKey, @RequestParam("authKey") String authKey, Model model) {
//        try {
//            // 1. 토스페이먼츠로부터 빌링키 발급
//            String billingKey = issueBillingKey(authKey, customerKey);
//
//            // 2. ✨ DB의 'SUBSCRIPTION' 테이블에 빌링키를 저장하는 서비스 로직 호출!
//            subscriptionService.saveBillingKey(customerKey, billingKey);
//
//            log.info("정기결제 카드 등록 및 DB 저장까지 모두 성공!");
//
//            model.addAttribute("message", "자동결제 카드 등록이 성공적으로 완료되었습니다.");
//            return "main/success"; // 성공 페이지로 이동
//
//        } catch (Exception e) {
//            log.error("빌링키 발급 또는 DB 저장 중 오류 발생. customerKey: {}", customerKey, e);
//            model.addAttribute("errorMessage", "카드 등록 처리 중 오류가 발생했습니다: " + e.getMessage());
//            model.addAttribute("errorCode", "BILLING_SETUP_FAILED");
//            return "main/fail"; // 실패 페이지로 이동
//        }
//    }

    //  2. [이 메서드도 추가하세요] 빌링키 발급 API를 호출하는 내부 헬퍼 메서드
    private String issueBillingKey(String authKey, String customerKey) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.tosspayments.com/v1/billing/authorizations/" + authKey;

        // HTTP 헤더 설정 (Basic Auth)
        HttpHeaders headers = new HttpHeaders();
        String encodedSecretKey = Base64.getEncoder().encodeToString((tossBilingSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // HTTP 요청 본문 설정
        Map<String, String> body = new HashMap<>();
        body.put("customerKey", customerKey);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // API 호출
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("billingKey");
        } else {
            throw new Exception("빌링키 발급 API 호출 실패: " + response.getBody());
        }
    }
 // 정기결제를 위한 임시 구독 정보를 생성하는 API
    @PostMapping("/create-pending")
    @ResponseBody
    public ResponseEntity<?> createPendingSubscription(@RequestBody SubscriptionVO subscriptionVO) {
        try {
            // 현재 로그인한 사용자 정보(mat_no)를 VO에 설정
            subscriptionVO.setMatNo(getLoggedInMatNo()); 

            SubscriptionVO createdSub = subscriptionService.createPendingSubscription(subscriptionVO);
            return ResponseEntity.ok(createdSub);
        } catch (Exception e) {
            log.error("임시 구독 정보 생성 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create subscription");
        }
    }
    
    @PostMapping("/cancel")
    @ResponseBody
    public ResponseEntity<?> cancelSubscription(@RequestBody Map<String, Object> requestPayload) {
        try {
            // Service로 Map을 그대로 전달
            Map<String, Object> result = subscriptionService.cancelSubscription(requestPayload);
            
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("구독 취소 처리 실패. Payload: {}", requestPayload, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", e.getMessage()));
        }
    }
    @GetMapping("/contract-html")
    @ResponseBody
    public String getContractHtml() throws IOException {
        // src/main/resources/templates/main/content.html 읽어서 반환
        ClassPathResource resource = new ClassPathResource("templates/main/content.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
//    // 구독 취소 (환불 없음)
//    @PostMapping("/cancel")
//    public ResponseEntity<?> cancelSubscription(@RequestBody CancelRequest req) {
//        try {
//            SubscriptionVO subscription = subscriptionService.getSubscription(req.getSubCode());
//
//            if (subscription == null) {
//                return ResponseEntity.badRequest().body(Map.of("message", "구독 정보를 찾을 수 없습니다."));
//            }
//
//            if (!"ACTIVE".equals(subscription.getSubStatus())) {
//                return ResponseEntity.badRequest().body(Map.of("message", "취소 가능한 상태가 아닙니다."));
//            }
//
//            // 구독 상태만 DB에서 취소 처리
//            subscriptionService.cancelSubscription(subscription.getSubCode());
//
//            return ResponseEntity.ok(Map.of(
//                "message", "구독이 정상적으로 취소되었습니다.",
//                "subCode", subscription.getSubCode()
//            ));
//
//        } catch (Exception e) {
//            log.error("구독 취소 실패", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body(Map.of("message", "구독 취소 중 오류가 발생했습니다."));
//        }
//    }
   }
