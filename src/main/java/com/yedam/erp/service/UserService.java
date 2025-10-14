package com.yedam.erp.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// Mapper, Service, Util 클래스 import
import com.yedam.erp.mapper.main.CompanyMapper;
import com.yedam.erp.mapper.main.EmpLoginMapper;
import com.yedam.erp.mapper.main.PasswordResetTokenMapper;
import com.yedam.erp.service.main.EmailService;
import com.yedam.erp.service.main.SmsService;
import com.yedam.erp.service.main.ValidationUtil;
// VO 및 DTO 클래스 import
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.EmpLoginVO;
import com.yedam.erp.vo.main.PasswordResetRequestVO;
import com.yedam.erp.vo.main.PwResetTokenVO;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmpLoginMapper empLoginMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private PasswordResetTokenMapper tokenMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * [1단계: SMS 발송 -> 인증번호 redis 저장 -> 컨트롤러 map<string,object>반환]
     */
    public Map<String, Object> sendSmsForPasswordReset(PasswordResetRequestVO requestDto) {
        Map<String, Object> response = new HashMap<>();
        String comCode = requestDto.getComCode();
        String empId = requestDto.getEmpId();

        try {
            // 1. 사용자 정보 및 휴대폰 번호 검증
            CompanyVO company = companyMapper.findByComCode(comCode)
                    .orElseThrow(() -> new NoSuchElementException("유효하지 않은 회사코드입니다."));
            
            EmpLoginVO foundUser = empLoginMapper.findByEmpIdAndComCode(empId, company.getComCode());
            if (foundUser == null) {
                throw new NoSuchElementException("아이디가 존재하지 않거나 회사 정보와 일치하지 않습니다.");
            }
            
            String phoneNum = foundUser.getEmpMobile();
            if (!StringUtils.hasText(phoneNum)) {
                throw new IllegalStateException("계정에 휴대폰 번호가 등록되어 있지 않습니다.");
            }

            // 2. 6자리 인증번호 생성
            String verificationCode = validationUtil.createCode();
            
            // 3. ★ Redis에 인증번호 저장 (Key: 고유ID, Value: 인증번호)
            String userKey = UUID.randomUUID().toString(); // 이 요청을 식별할 고유 키
            String redisKey = "sms_auth:" + userKey;
            
            redisTemplate.opsForValue().set(
                redisKey, 
                verificationCode, 
                Duration.ofMinutes(3) //3분 후 자동 만료
            );

            // 4. SMS 발송
            String messageText = "[ERP] 비밀번호 찾기 인증번호는 [" + verificationCode + "] 입니다.";
            boolean isSuccess = smsService.sendSms(phoneNum, messageText);
            if (!isSuccess) {
                throw new RuntimeException("SMS 발송 시스템에서 오류가 발생했습니다.");
            }

            logger.info("인증번호 Redis 저장 및 SMS 발송 성공: empId={}", empId);
            response.put("success", true);
            response.put("message", "등록된 휴대폰 번호로 인증번호를 발송했습니다.");
            response.put("userKey", userKey); // ★ 클라이언트가 다음 단계에서 사용할 userKey 전달
            return response;

        } catch (NoSuchElementException | IllegalStateException e) { // 사용자가 식별한 오류
            logger.warn("사용자 검증 실패: comCode={}, empId={}, error={}", comCode, empId, e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        } catch (Exception e) { // 서버 내부 오류
            logger.error("비밀번호 재설정 SMS 발송 중 오류 발생: comCode={}, empId={}", comCode, empId, e);
            response.put("success", false);
            response.put("message", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return response;
        }
    }

    /**
     * [2단계: SMS 검증(Redis) 및 이메일 토큰 발송(DB)]
     * Controller의 예외 처리를 위해 실패 시 Exception을 발생시킵니다.
     */
    @Transactional
    public void verifySmsAndSendResetEmail(PasswordResetRequestVO requestVO) {
        
        // 1. ★ Redis에서 인증번호 조회
        String redisKey = "sms_auth:" + requestVO.getUserKey();
        String savedCode = redisTemplate.opsForValue().get(redisKey);

        // 2. ★ 인증번호 검증
        if (savedCode == null) {
            throw new IllegalArgumentException("인증 시간이 만료되었습니다. 다시 시도해주세요.");
        }
        if (!savedCode.equals(requestVO.getSmsCode())) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        // 3. 사용자 정보 조회 (DB에서 PK인 empIdNo를 가져오기 위함)
        EmpLoginVO foundUser = empLoginMapper.findByEmpIdAndComCode(requestVO.getEmpId(), requestVO.getComCode());
        if (foundUser == null || foundUser.getEmpIdNo() == null) {
            throw new NoSuchElementException("사용자 정보를 찾을 수 없습니다."); // 2단계에서 정보가 없을 리 없지만 방어 코드
        }

        // 4. ★ 1회용 토큰 생성 및 DB 저장 (pw_rest_token 테이블)
        String token = UUID.randomUUID().toString();
        PwResetTokenVO resetToken = new PwResetTokenVO();
        resetToken.setToken(token);
        resetToken.setEmpIdNo(foundUser.getEmpIdNo()); // ★ 사용자의 PK (emp_id_no)
        resetToken.setStartDate(LocalDateTime.now());
        resetToken.setEndDate(LocalDateTime.now().plusMinutes(30)); // ★ 30분 유효기간
        
        tokenMapper.save(resetToken); // PwResetTokenMapper.xml의 'save' 쿼리 실행

        // 5. 비밀번호 재설정 링크가 포함된 이메일 발송
        String resetLink = "http://localhost/reset-password?token=" + token; // TODO: 실제 도메인으로 변경
        String emailBody = "안녕하세요.\n비밀번호를 재설정하려면 아래 링크를 클릭하세요.\n이 링크는 30분 동안 유효합니다.\n\n" + resetLink;
        
        emailService.sendSimpleMessage(requestVO.getMatMail(), "[ERP 시스템] 비밀번호 재설정 안내", emailBody);
        
        // 6. ★ 성공 시 Redis에 저장된 인증번호 즉시 삭제 (재사용 방지)
        redisTemplate.delete(redisKey);

        logger.info("SMS 인증 성공 및 비밀번호 재설정 이메일 발송 완료: empId={}", requestVO.getEmpId());
    }

    /**
     * [3단계: 토큰 유효성 검증]
     * (사용자가 이메일 링크를 클릭하여 비밀번호 재설정 페이지에 접근할 때 사용)
     */
    public PwResetTokenVO validatePasswordResetToken(String token) {
        // PwResetTokenMapper.xml의 'findTokenByTokenString' 쿼리 실행
        PwResetTokenVO resetToken = tokenMapper.findByToken(token);
        
        // 토큰이 없거나, 만료 시간이 현재 시간 이전(isBefore)이라면 null 반환
        if (resetToken == null || resetToken.getEndDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        return resetToken;
    }

    /**
     * [4단계: 비밀번호 최종 변경]
     * (사용자가 새 비밀번호를 입력하고 저장할 때 사용)
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // PwResetTokenMapper.xml의 'findTokenByTokenString' 쿼리 실행
        PwResetTokenVO resetToken = tokenMapper.findByToken(token);
        
        if (resetToken == null) {
            throw new IllegalStateException("유효하지 않은 토큰입니다.");
        }
        
        // (만료 검증)
        if (resetToken.getEndDate().isBefore(LocalDateTime.now())) {
             tokenMapper.deleteByToken(token); // 만료된 토큰 삭제
             throw new IllegalStateException("만료된 토큰입니다.");
        }

        // 1. 새 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newPassword);
        
        // 2. EmpLoginMapper를 통해 DB의 비밀번호 업데이트
        empLoginMapper.updatePasswordByEmpIdNo(resetToken.getEmpIdNo(), encodedPassword);
        logger.info("비밀번호 변경 완료: empIdNo={}", resetToken.getEmpIdNo());

        // 3. 사용 완료된 토큰 DB에서 삭제
        tokenMapper.deleteByToken(token); // PwResetTokenMapper.xml의 'deleteToken' 쿼리 실행
    }
}
//---수정전
//    public ResponseEntity<String> sendSmsForPasswordReset(PasswordResetRequestVO requestDto) {
//        // (기존 코드와 동일)
//        String comCode = requestDto.getComCode();
//        String empId = requestDto.getEmpId();
//
//        try {
//            CompanyVO company = companyMapper.findByComCode(comCode)
//                    .orElseThrow(() -> new NoSuchElementException("유효하지 않은 회사코드입니다."));
//            logger.info("회사 정보 조회 성공: comCode={}", comCode);
//
//            EmpLoginVO foundUser = empLoginMapper.findByEmpIdAndComCode(empId, company.getComCode());
//            if (foundUser == null) {
//                throw new NoSuchElementException("아이디가 존재하지 않거나 회사 정보와 일치하지 않습니다.");
//            }
//            logger.info("사용자 정보 조회 성공: empId={}", empId);
//
//            String phoneNum = foundUser.getEmpMobile();
//            if (!StringUtils.hasText(phoneNum)) {
//                logger.warn("사용자에게 휴대폰 번호가 등록되어 있지 않습니다. empId={}", empId);
//                return ResponseEntity.badRequest().body("계정에 휴대폰 번호가 등록되어 있지 않습니다.");
//            }
//
//            String verificationCode = validationUtil.createCode();
//            String messageText = "[ERP] 비밀번호 찾기 인증번호는 [" + verificationCode + "] 입니다.";
//
//            String phone = phoneNum.replaceAll("[^0-9]", "");
//            if (phone.startsWith("0")) {
//                phone = "+82" + phone.substring(1);
//            }
//
//            boolean isSuccess = smsService.sendSms(phone, messageText);
//            if (!isSuccess) {
//                throw new IllegalStateException("SMS 발송 시스템에서 오류가 발생했습니다.");
//            }
//
//            logger.info("비밀번호 재설정 SMS 발송 성공: empId={}, phoneNum={}", empId, phone);
//            return ResponseEntity.ok("등록된 휴대폰 번호로 인증번호를 발송했습니다.");
//
//        } catch (NoSuchElementException e) {
//            logger.warn("사용자 검증 실패: comCode={}, empId={}, error={}", comCode, empId, e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("비밀번호 재설정 SMS 발송 중 오류 발생: comCode={}, empId={}", comCode, e);
//            return ResponseEntity.status(500).body("서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
//        }
//    }
//
//    /**
//     * [2단계: SMS 검증 및 이메일 토큰 발송]
//     */
//    public ResponseEntity<String> verifySmsAndSendResetEmail(String comCode, String empId, String matMail, String smsCode) {
//        try {
//            // TODO: 1. SMS 인증번호 검증 로직 구현
//            EmpLoginVO foundUser = empLoginMapper.findByEmpIdAndComCode(empId, comCode);
//            if (foundUser == null || foundUser.getEmpIdNo() == null) {
//                throw new NoSuchElementException("사용자 정보를 찾을 수 없습니다.");
//            }
//
//            // 3. 1회용 토큰 생성 및 DB 저장
//            String token = UUID.randomUUID().toString();
//            // PwResetTokenVO -> PasswordResetTokenVO 로 변경
//            PwResetTokenVO resetToken = new PwResetTokenVO();
//            resetToken.setToken(token);
//            resetToken.setEmpIdNo(foundUser.getEmpIdNo());
//            resetToken.setStartDate(LocalDateTime.now());
//            resetToken.setEndDate(LocalDateTime.now().plusMinutes(30));
//            tokenMapper.save(resetToken);
//
//            // 4. 비밀번호 재설정 링크가 포함된 이메일 발송
//            String resetLink = "http://localhost:8080/reset-password?token=" + token;
//            String emailBody = "안녕하세요.\n비밀번호를 재설정하려면 아래 링크를 클릭하세요.\n이 링크는 30분 동안 유효합니다.\n\n" + resetLink;
//            emailService.sendSimpleMessage(matMail, "[ERP 시스템] 비밀번호 재설정 안내", emailBody);
//
//            logger.info("비밀번호 재설정 이메일 발송 성공: empId={}", empId);
//            return ResponseEntity.ok("인증이 완료되었습니다. 이메일을 확인하여 비밀번호 재설정을 완료해주세요.");
//
//        } catch (NoSuchElementException e) {
//            logger.warn("사용자 검증 실패: empId={}, error={}", empId, e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("비밀번호 재설정 이메일 발송 중 오류 발생: empId={}", empId, e);
//            return ResponseEntity.status(500).body("처리 중 오류가 발생했습니다.");
//        }
//    }
//
//    /**
//     * [3단계: 토큰 유효성 검증]
//     */
//    // PwResetTokenVO -> PasswordResetTokenVO 로 변경
//    public PwResetTokenVO validatePasswordResetToken(String token) {
//    	PwResetTokenVO resetToken = tokenMapper.findByToken(token);
//        if (resetToken == null || resetToken.getEndDate().isBefore(LocalDateTime.now())) {
//            return null;
//        }
//        return resetToken;
//    }
//
//    /**
//     * [4단계: 비밀번호 최종 변경]
//     */
//    public void resetPassword(String token, String newPassword) {
//        // PwResetTokenVO -> PasswordResetTokenVO 로 변경
//    	PwResetTokenVO resetToken = tokenMapper.findByToken(token);
//        if (resetToken == null) {
//            throw new IllegalStateException("유효하지 않은 토큰입니다.");
//        }
//
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        empLoginMapper.updatePasswordByEmpIdNo(resetToken.getEmpIdNo(), encodedPassword);
//        logger.info("비밀번호 변경 완료: empIdNo={}", resetToken.getEmpIdNo());
//
//        tokenMapper.deleteByToken(token);
//    }
