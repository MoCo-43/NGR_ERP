// LoginController.java

package com.yedam.erp.web.ApiController;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.erp.service.main.LoginService;
import com.yedam.erp.service.main.NaverCaptchaService;
import com.yedam.erp.service.main.RecaptchaService;

@Controller
public class LoginController {

    @Autowired
    private RecaptchaService recaptchaService;
    @Autowired
    private  NaverCaptchaService naverCaptchaService;

    @Autowired
    private LoginService loginService;
    //보안문자 이미지 + 정답 확인 필요한 고유 키 -> 생성된 이미지와 키 model담아 main/login.html페이지로 전달-> 화면 보안문자 포함된 로그인 화면 확인
    @GetMapping("/login")
    public String showLoginPage(Model model) throws IOException {
        String captchaKey = naverCaptchaService.getCaptchaKey();
        System.out.println(""+captchaKey);
        byte[] imageBytes = naverCaptchaService.getCaptchaImage(captchaKey);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        model.addAttribute("captchaKey", captchaKey);
        model.addAttribute("captchaImage", base64Image);
        return "main/login";
    }

    @GetMapping("/captcha")
    @ResponseBody
    public String handleCaptchaRequest() {
        return "captcha_ok";
    }
    
    // 이전에 있던 @PostMapping("/login") 메서드는 시큐리티가 DB조회 후 비밀번호 비교를 진행해주는데 중복되서 로그인 흐름 꼬이는 문제로 인해 제거
}