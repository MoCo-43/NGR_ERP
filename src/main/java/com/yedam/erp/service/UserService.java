package com.yedam.erp.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Mapper, Service, Util 클래스 import
import com.yedam.erp.mapper.main.CompanyMapper;
import com.yedam.erp.mapper.main.EmpLoginMapper;
import com.yedam.erp.service.main.SmsService;
import com.yedam.erp.service.main.ValidationUtil;

// VO 및 DTO 클래스 import
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.EmpLoginVO;
import com.yedam.erp.vo.main.PasswordResetRequestVO;

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

    /**
     * 비밀번호 재설정을 위해 회사번호와 사원아이디를 검증하고 SMS 인증번호를 보냅니다.
     * @param requestDto 회사번호(matNo)와 사원아이디(empId)가 담긴 DTO
     * @return SMS 전송 결과
     */
    public ResponseEntity<String> sendSmsForPasswordReset(PasswordResetRequestVO requestDto) {
        Long matNo = requestDto.getMatNo();
        String empId = requestDto.getEmpId();
        
        try {
            // 1. 회사 정보 조회
            Optional<CompanyVO> companyOptional = companyMapper.findByMatNo(matNo);
            CompanyVO company = companyOptional
                    .orElseThrow(() -> new NoSuchElementException("유효하지 않은 회사코드입니다."));
            logger.info("회사 정보 조회 성공: matNo={}", matNo);

            // 2. 사용자 조회
            EmpLoginVO foundUser = empLoginMapper.findByEmpIdAndMatNo(empId, company.getMatNo());
            if (foundUser == null) {
                throw new NoSuchElementException("아이디가 존재하지 않거나 회사 정보와 일치하지 않습니다.");
            }
            logger.info("사용자 정보 조회 성공: empId={}", empId);

            // 3. 휴대폰 번호 조회 
            // 🔴 수정된 부분: emp_mobile 필드에서 휴대폰 번호를 가져옵니다.
            String phoneNum = foundUser.getEmpMobile(); 
            
            if (!StringUtils.hasText(phoneNum)) {
                logger.warn("사용자에게 휴대폰 번호가 등록되어 있지 않습니다. empId={}", empId);
                return ResponseEntity.badRequest().body("계정에 휴대폰 번호가 등록되어 있지 않습니다.");
            }

            // 4. 휴대폰 번호 정리 및 국제번호 변환
            String phone = phoneNum.replaceAll("[^0-9]", ""); // 숫자만 남김
            if (phone.startsWith("0")) {
                phone = "+82" + phone.substring(1); // 01011112222 → +821011112222
            }

            // 5. 인증번호 생성 및 SMS 발송
            String verificationCode = validationUtil.createCode();
            String messageText = "[테스트] 비밀번호 찾기 인증번호는 [" + verificationCode + "] 입니다.";

            boolean isSuccess = smsService.sendSms(phone, messageText);
            if (!isSuccess) {
                throw new IllegalStateException("SMS 발송 시스템에서 오류가 발생했습니다.");
            }

            logger.info("비밀번호 재설정 SMS 발송 성공: empId={}, phoneNum={}", empId, phone);
            return ResponseEntity.ok("등록된 휴대폰 번호로 인증번호를 발송했습니다.");

        } catch (NoSuchElementException e) {
            logger.warn("사용자 검증 실패: matNo={}, empId={}, error={}", matNo, empId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("비밀번호 재설정 SMS 발송 중 오류 발생: matNo={}, empId={}", matNo, empId, e);
            return ResponseEntity.status(500).body("서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    }
}