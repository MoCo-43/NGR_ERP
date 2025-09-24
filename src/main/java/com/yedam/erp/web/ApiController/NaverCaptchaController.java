//네이버캡차기능 모아둔 컨트롤러
package com.yedam.erp.web.ApiController;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.erp.service.main.NaverCaptchaService;

@Controller
public class NaverCaptchaController {

    private final NaverCaptchaService naverCaptchaService;

    public NaverCaptchaController(NaverCaptchaService naverCaptchaService) {
        this.naverCaptchaService = naverCaptchaService;
    }
    
    @GetMapping("/naver-captcha")
    public String showLoginForm(Model model) throws IOException {
        String captchaKey = naverCaptchaService.getCaptchaKey();
        byte[] imageBytes = naverCaptchaService.getCaptchaImage(captchaKey);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        model.addAttribute("captchaKey", captchaKey);
        model.addAttribute("captchaImage", base64Image);
        return "main/login"; // login.html
    }
    //보안문자 검증 처리,성공유무와 새로운 보안문자이미지/키를 model담아 다시 login.html페이지를 보여줌
    @PostMapping("/validate")
    public String validateCaptcha(@RequestParam("captchaKey") String key,
                                  @RequestParam("captchaValue") String value,
                                  Model model) throws IOException {
        boolean isValid = naverCaptchaService.validateCaptcha(key, value);
        model.addAttribute("captchaResult", isValid ? "CAPTCHA 검증 성공! ✅" : "CAPTCHA 검증 실패! ❌");

        // 새로운 CAPTCHA 생성
        String newCaptchaKey = naverCaptchaService.getCaptchaKey();
        byte[] newImageBytes = naverCaptchaService.getCaptchaImage(newCaptchaKey);
        String newBase64Image = Base64.getEncoder().encodeToString(newImageBytes);
        model.addAttribute("captchaKey", newCaptchaKey);
        model.addAttribute("captchaImage", newBase64Image);

        return "main/login"; // login.html
    }
    //보안문자 새로고침 기능-Map형태-json형식으로 반환되어 브라우저 전달
    @GetMapping("/refreshCaptcha")
    @ResponseBody
    public Map<String, String> refreshCaptcha() throws IOException {
        String newCaptchaKey = naverCaptchaService.getCaptchaKey();
        byte[] newImageBytes = naverCaptchaService.getCaptchaImage(newCaptchaKey);
        String newBase64Image = Base64.getEncoder().encodeToString(newImageBytes);

        Map<String, String> response = new HashMap<>();
        response.put("key", newCaptchaKey);
        response.put("image", newBase64Image);

        return response; // JSON 자동 직렬화
    }
}