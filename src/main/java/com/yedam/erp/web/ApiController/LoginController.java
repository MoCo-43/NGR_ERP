// LoginController.java

package com.yedam.erp.web.ApiController;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yedam.erp.service.UserService;
import com.yedam.erp.service.main.LoginService;
import com.yedam.erp.service.main.NaverCaptchaService;
import com.yedam.erp.service.main.RecaptchaService;
import com.yedam.erp.vo.main.PasswordResetRequestVO;

@Controller
public class LoginController {

    @Autowired
    private RecaptchaService recaptchaService;
    @Autowired
    private  NaverCaptchaService naverCaptchaService;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserService userService;
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

    @GetMapping("/findpw")
    public String showForgotPasswordForm() {
        return "main/forgotpasword"; // Thymeleaf 뷰 이름
    }
    /**
     * 비밀번호 찾기 폼 요청을 처리하여 SMS 인증번호를 발송합니다.
     * @param comCode 회사 코드
     * @param empId 사원 아이디
     * @param redirectAttributes 리다이렉트 시 메시지 전달을 위한 객체
     * @return 성공/실패에 따른 리다이렉트 경로
     */
    @PostMapping("/findpw")
    public String handleForgotPassword(
        @RequestParam("comCode") String comCode, // 파라미터를 Long 타입의 matNo로 받습니다.
        @RequestParam("empId") String empId,
        RedirectAttributes redirectAttributes) {

        // 1. DTO 객체 생성 및 데이터 설정
        PasswordResetRequestVO requestDto = new PasswordResetRequestVO();
        requestDto.setComCode(comCode);
        requestDto.setEmpId(empId);
        
        // 2. UserService 호출하여 SMS 발송 로직 실행
        ResponseEntity<String> response = userService.sendSmsForPasswordReset(requestDto);
        
        // 3. 서비스 처리 결과에 따라 메시지 설정 및 리다이렉트
        if (response.getStatusCode() == HttpStatus.OK) {
            redirectAttributes.addFlashAttribute("successMessage", response.getBody());
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", response.getBody());
            return "redirect:/findpw";
        }
    }

    // 이전에 있던 @PostMapping("/login") 메서드는 시큐리티가 DB조회 후 비밀번호 비교를 진행해주는데 중복되서 로그인 흐름 꼬이는 문제로 인해 제거
}