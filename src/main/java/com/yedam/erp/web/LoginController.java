// LoginController.java

package com.yedam.erp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.erp.service.LoginService;
import com.yedam.erp.service.RecaptchaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private RecaptchaService recaptchaService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "main/login";
    }

    @GetMapping("/captcha")
    @ResponseBody
    public String handleCaptchaRequest() {
        return "captcha_ok";
    }
    
    // 이전에 있던 @PostMapping("/login") 메서드는 삭제되었습니다.
}