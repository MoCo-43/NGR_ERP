// LoginController.java

package com.yedam.erp.web.ApiController.main;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.erp.service.UserService;
import com.yedam.erp.service.main.EmpLoginService;
import com.yedam.erp.service.main.LoginService;
import com.yedam.erp.service.main.NaverCaptchaService;
import com.yedam.erp.service.main.RecaptchaService;
import com.yedam.erp.vo.hr.EmpVO;
import com.yedam.erp.vo.main.PasswordResetRequestVO;
import com.yedam.erp.vo.main.PwResetTokenVO;

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
    
    //일정관리
    @GetMapping("/mypage/schedules")
    public String schedules() {
    	return "main/schedules";
    }
    

    //마이페이지
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
            log.warn("[경고] 사용자 ID: {} 에 대한 사원 정보(EmpVO)가 DB에서 발견되지 않았습니다.", empId);
            
            // 안전한 템플릿 렌더링을 위해 빈 객체로 초기화
            emp = new EmpVO(); 
        } else {
            log.info("사용자 ID: {} 에 대한 사원 정보 로드 성공.", empId);
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
    @GetMapping("/salLogin")
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
//    /**
//     * 비밀번호 찾기 폼 요청을 처리하여 SMS 인증번호를 발송합니다.
//     * @param comCode 회사 코드
//     * @param empId 사원 아이디
//     * @param redirectAttributes 리다이렉트 시 메시지 전달을 위한 객체
//     * @return 성공/실패에 따른 리다이렉트 경로
//     */
//    @PostMapping("/findpw")
//    public String handleForgotPassword(
//        @RequestParam("comCode") String comCode, // 파라미터를 Long 타입의 matNo로 받습니다.
//        @RequestParam("empId") String empId,
//        @RequestParam("matMail") String matMail,
//        RedirectAttributes redirectAttributes) {
//
//        // 1.  객체 생성 및 데이터 설정
//        PasswordResetRequestVO requestVO = new PasswordResetRequestVO();
//        requestVO.setComCode(comCode);
//        requestVO.setEmpId(empId);
//        
//        // 2. UserService 호출하여 SMS 발송 로직 실행
//        ResponseEntity<String> response = userService.sendSmsForPasswordReset(requestVO);
//        
//        // 3. 서비스 처리 결과에 따라 메시지 설정 및 리다이렉트
//        if (response.getStatusCode() == HttpStatus.OK) {
//            redirectAttributes.addFlashAttribute("successMessage", response.getBody());
//            return "redirect:/login";
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", response.getBody());
//            return "redirect:/findpw";
//        }
//    }

//    // Step 1: SMS 발송
//    @PostMapping("/sendSms")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> sendSms(@RequestParam String comCode,
//                                                       @RequestParam String empId,
//                                                       @RequestParam(required = false) String matMail) {
//        PasswordResetRequestVO requestVO = new PasswordResetRequestVO();
//        requestVO.setComCode(comCode);
//        requestVO.setEmpId(empId);
//        requestVO.setMatMail(matMail);
//
//        Map<String, Object> result = new HashMap<>();
//
//        try {
//            // userService에서 SMS 발송 후 userKey 반환하도록 구현했다고 가정
//            String userKey = userService.sendSmsForPasswordReset(requestVO).getBody();
//
//            result.put("success", true);
//            result.put("message", "인증번호가 발송되었습니다.");
//            result.put("userKey", userKey); // STEP2 hidden input에 넣을 값
//            return ResponseEntity.ok(result);
//
//        } catch (Exception e) {
//            result.put("success", false);
//            result.put("message", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
//        }
//    }
    // Step 2: 인증번호 검증
//    @PostMapping("/pwngremail")
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
    /**
     * ★ [1단계: SMS 발송 요청]
     * forgotpasword.html의 1단계 fetch 요청을 받습니다.
     * @RequestBody로 JSON 데이터를 받습니다.
     * Map<String, Object>를 JSON으로 반환합니다.
     */
    @PostMapping("/sendSms")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendSms(@RequestBody PasswordResetRequestVO requestVO) {
        Map<String, Object> result;
        try {
            // ★ UserService 1단계 호출 (Service가 userKey 포함된 Map 반환)
            result = userService.sendSmsForPasswordReset(requestVO);
            
            boolean success = (boolean) result.getOrDefault("success", false);
            
            if (success) {
                // 성공: {success: true, message: "...", userKey: "..."}
                return ResponseEntity.ok(result);
            } else {
                // 실패: {success: false, message: "사용자 없음 등"}
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        } catch (Exception e) {
            // UserService 내부의 알 수 없는 서버 오류
            log.error("SMS 발송 컨트롤러 오류", e);
            result = new HashMap<>();
            result.put("success", false);
            result.put("message", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
    
    /**
     * ★ [2단계: 인증번호 검증 및 이메일 발송 요청]
     * forgotpasword.html의 2단계 fetch 요청을 받습니다.
     * @RequestBody로 JSON 데이터를 받습니다.
     * UserService의 예외(Exception)를 감지하여 처리합니다.
     */
    @PostMapping("/pwngremail")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyTokenAndSendEmail(
                                              @RequestBody PasswordResetRequestVO requestVO) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            // ★ UserService 2단계 호출 (성공 시 void, 실패 시 예외 발생)
            userService.verifySmsAndSendResetEmail(requestVO);
            
            // --- 성공 시 ---
            response.put("success", true);
            response.put("message", "인증이 완료되었습니다. 비밀번호 재설정 링크가 이메일로 전송되었습니다.");
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException | NoSuchElementException e) { 
            // ★ UserService에서 의도적으로 발생시킨 '인증 실패' 관련 예외
            // (예: "인증 시간이 만료되었습니다.", "인증번호가 일치하지 않습니다.")
            log.warn("비밀번호 찾기 인증 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (Exception e) { 
            // ★ 그 외 서버 내부 오류 (DB 연결 실패, 이메일 서버 접속 실패 등)
            log.error("비밀번호 찾기 2단계 처리 중 예외 발생", e);
            response.put("success", false);
            response.put("message", "처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /**
     * [3단계: 비밀번호 재설정 페이지 표시]
     * 이메일 링크를 클릭했을 때, 새 비밀번호를 입력하는 폼(HTML)을 보여줍니다.
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        
        // 1. 토큰이 유효한지 UserService를 통해 검증
        PwResetTokenVO resetToken = userService.validatePasswordResetToken(token);
        
        if (resetToken == null) {
            // 토큰이 유효하지 않거나 만료됨
            model.addAttribute("errorMessage", "유효하지 않거나 만료된 토큰입니다.");
            return "main/login"; // 오류 메시지와 함께 로그인 페이지로
        }
        
        // 2. 토큰이 유효하면, 해당 토큰 값을 모델에 담아서
        //    새 비밀번호를 입력할 페이지로 이동
        model.addAttribute("token", token);
        return "main/resetPasswordPage"; // ★ templates/main/resetPasswordPage.html
    }
    /**
     * [4단계: 비밀번호 최종 변경]
     * resetPasswordPage.html에서 새 비밀번호를 받아 처리합니다.
     */
    @PostMapping("/reset-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleResetPassword(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = payload.get("token");
            String newPassword = payload.get("newPassword");

            if (token == null || newPassword == null) {
                throw new IllegalArgumentException("필요한 정보가 누락되었습니다.");
            }

            // ★ UserService의 4단계 메서드 호출
            userService.resetPassword(token, newPassword);

            response.put("success", true);
            response.put("message", "비밀번호가 성공적으로 변경되었습니다. 로그인 페이지로 이동합니다.");
            return ResponseEntity.ok(response);

        } catch (IllegalStateException | IllegalArgumentException e) {
            // "유효하지 않은 토큰입니다." 또는 "만료된 토큰입니다." 등
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("비밀번호 최종 변경 중 오류", e);
            response.put("success", false);
            response.put("message", "서버 오류로 비밀번호 변경에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
    
    // 이전에 있던 @PostMapping("/login") 메서드는 시큐리티가 DB조회 후 비밀번호 비교를 진행해주는데 중복되서 로그인 흐름 꼬이는 문제로 인해 제거