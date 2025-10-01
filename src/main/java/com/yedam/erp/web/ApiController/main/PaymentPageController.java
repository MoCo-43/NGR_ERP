package com.yedam.erp.web.ApiController.main;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentPageController {

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            Model model) {
        
        // 받은 파라미터들을 모델에 담아 success.html 템플릿으로 전달
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "main/success"; // templates/success.html 을 찾아서 렌더링
    }
    //실패 페이지
    @GetMapping("/payment/fail")
    public String paymentFail(
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String code,
            Model model) {

        model.addAttribute("message", message);
        model.addAttribute("code", code);

        return "main/fail"; // templates/main/fail.html
    }
}