// LoginController.java

package com.yedam.erp.web.ApiController.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yedam.erp.service.UserService;
import com.yedam.erp.service.main.EmpLoginService;
import com.yedam.erp.service.main.LoginService;
import com.yedam.erp.service.main.NaverCaptchaService;
import com.yedam.erp.service.main.RecaptchaService;
import com.yedam.erp.vo.hr.EmpVO;
import com.yedam.erp.vo.main.PasswordResetRequestVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
    
    @Autowired
    private EmpLoginService empLoginService;
    
    @GetMapping("/mypage/mylist")
    public String mypage(
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) throws Exception {
        
        // 1. 로그인된 사용자 ID를 인증 객체에서 가져옴
        String empId = userDetails.getUsername(); 
        
        log.info("마이페이지 요청 - 로그인된 사용자 ID (empId): {}", empId);
        
        // 2. 서비스 로직은 empId를 사용하여 그대로 진행
        EmpVO emp = empLoginService.mypageInfo(empId);
        
        // 3. EmpVO가 null일 때 템플릿 오류가 발생하지 않도록 Null 체크
        if (emp == null) {
            log.warn("⚠️ [경고] 사용자 ID: {} 에 대한 사원 정보(EmpVO)가 DB에서 발견되지 않았습니다.", empId);
            
            // 안전한 템플릿 렌더링을 위해 빈 객체로 초기화
            emp = new EmpVO(); 
        } else {
            log.info("✔️ 사용자 ID: {} 에 대한 사원 정보 로드 성공.", empId);
            log.debug("   - 이름: {}", emp.getName());
            log.debug("   - 사원 번호: {}", emp.getEmp_id());
            log.debug("   - 직급: {}", emp.getPosition());
        }

        model.addAttribute("emp", emp);
        
        return "main/mypage"; // templates/main/mypage.html
    }
    
    
    //보안문자 이미지 + 정답 확인 필요한 고유 키 -> 생성된 이미지와 키 model담아 main/login.html페이지로 전달-> 화면 보안문자 포함된 로그인 화면 확인
    @GetMapping("/login")
    public String showLoginPage() {
        // 관리자 버튼 클릭 시만 CAPTCHA 생성되므로 초기 로드 시 생성하지 않음
        return "main/login";
    }
    @GetMapping("/sal/salLogin")
    public String sallogin() {
        // 관리자 버튼 클릭 시만 CAPTCHA 생성되므로 초기 로드 시 생성하지 않음
        return "main/salLogin";
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
        @RequestParam("matMail") String matMail,
        RedirectAttributes redirectAttributes) {

        // 1.  객체 생성 및 데이터 설정
        PasswordResetRequestVO requestVO = new PasswordResetRequestVO();
        requestVO.setComCode(comCode);
        requestVO.setEmpId(empId);
        
        // 2. UserService 호출하여 SMS 발송 로직 실행
        ResponseEntity<String> response = userService.sendSmsForPasswordReset(requestVO);
        
        // 3. 서비스 처리 결과에 따라 메시지 설정 및 리다이렉트
        if (response.getStatusCode() == HttpStatus.OK) {
            redirectAttributes.addFlashAttribute("successMessage", response.getBody());
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", response.getBody());
            return "redirect:/findpw";
        }
    }

    // Step 1: SMS 발송
    @PostMapping("/sendSms")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendSms(@RequestParam String comCode,
                                                       @RequestParam String empId,
                                                       @RequestParam(required = false) String matMail) {
        PasswordResetRequestVO requestVO = new PasswordResetRequestVO();
        requestVO.setComCode(comCode);
        requestVO.setEmpId(empId);
        requestVO.setMatMail(matMail);

        Map<String, Object> result = new HashMap<>();

        try {
            // userService에서 SMS 발송 후 userKey 반환하도록 구현했다고 가정
            String userKey = userService.sendSmsForPasswordReset(requestVO).getBody();

            result.put("success", true);
            result.put("message", "인증번호가 발송되었습니다.");
            result.put("userKey", userKey); // STEP2 hidden input에 넣을 값
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
    // Step 2: 인증번호 검증
//    @PostMapping("/findpw/verify-token")
//    @ResponseBody
//    public ResponseEntity<String> verifyToken(@RequestParam String userKey,
//                                              @RequestParam String smsCode) {
//        boolean valid = userService.verifySmsCode(userKey, smsCode);
//
//        if(valid) {
//            userService.sendPasswordResetEmail(userKey);
//            return ResponseEntity.ok("비밀번호 재설정 링크가 이메일로 전송되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .body("인증번호가 올바르지 않습니다.");
//        }
//    }
}
    
    // 이전에 있던 @PostMapping("/login") 메서드는 시큐리티가 DB조회 후 비밀번호 비교를 진행해주는데 중복되서 로그인 흐름 꼬이는 문제로 인해 제거