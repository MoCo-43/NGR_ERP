//package com.yedam.erp.service.impl.main;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.yedam.erp.mapper.main.EmpLoginMapper;
//import com.yedam.erp.service.main.EmailService;
//import com.yedam.erp.service.main.EmpLoginService;
//import com.yedam.erp.vo.hr.EmpVO;
//import com.yedam.erp.vo.main.EmpLoginVO;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class EmpLoginServiceImpl implements EmpLoginService {
//
//    private final EmpLoginMapper empLoginMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;
//
//    @Override
//    public List<EmpVO> findEmployeesByDept(String deptCode) {
//        return empLoginMapper.findEmployeesByDept(deptCode);
//    }
//
//    @Override
//    @Transactional
//    public void activateDefaultLogin(String empId) {
//        activateLogin(empId, empId);
//    }
//
//    @Override
//    @Transactional
//    public void activateCustomLogin(String empId) {
//        String customLoginId = "gen-" + empId;
//        activateLogin(empId, customLoginId);
//    }
//
//    @Override
//    @Transactional
//    public void changePassword(String empId, String newPassword) {
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        empLoginMapper.updatePasswordAndFlag(empId, encodedPassword, "N");
//    }
//
//    @Override
//    @Transactional
//    public void insertNewEmployeeLogin(EmpLoginVO empLoginVO) {
//        // 관리자 계정 생성도 activateLogin으로 통합
//        EmpVO emp = empLoginMapper.findEmployeeById(String.valueOf(empLoginVO.getEmpNo()));
//        if(emp == null) {
//            throw new RuntimeException("사원 정보를 찾을 수 없습니다: " + empLoginVO.getEmpNo());
//        }
//
//        // matNo가 VO에 없으면 emp에서 가져오기
//        if(empLoginVO.getMatNo() == null) {
//            empLoginVO.setMatNo(emp.getCompanyCode());
//        }
//
//        // activateLogin 재사용
//        activateLogin(String.valueOf(empLoginVO.getEmpNo()), empLoginVO.getEmpId());
//    }
//
//    @Override
//    public EmpVO findEmployeeById(String empId) {
//        return empLoginMapper.findEmployeeById(empId);
//    }
//
//    // ===================== 공통 로직 =====================
//    private void activateLogin(String employeeId, String loginId) {
//        EmpVO emp = empLoginMapper.findEmployeeById(employeeId);
//        if(emp == null) throw new RuntimeException("사원 정보를 찾을 수 없습니다: " + employeeId);
//
//        // 임시 비밀번호 생성 및 암호화
//        String tempPassword = generateRandomPassword();
//        String encodedPassword = passwordEncoder.encode(tempPassword);
//
//        // 로그인 VO 세팅
//        EmpLoginVO loginVO = new EmpLoginVO();
//        loginVO.setEmpId(loginId);
//        loginVO.setEmpNo(Long.parseLong(employeeId));
//        loginVO.setEmpPw(encodedPassword);
//        loginVO.setMustChangePw("Y");
//        loginVO.setMatNo(emp.getCompanyCode()); // matNo 세팅
//
//        // DB 저장
//        empLoginMapper.insertNewEmployeeLogin(loginVO);
//
//        // 이메일 발송
//        sendInitialPasswordEmail(emp, loginId, tempPassword);
//    }
//
//    private void sendInitialPasswordEmail(EmpVO emp, String loginId, String tempPassword) {
//        try {
//            String subject = "[ERP 시스템] 계정 활성화 안내";
//            String text = "안녕하세요, " + emp.getName() + "님.\n\n"
//                    + "로그인 ID: " + loginId + "\n"
//                    + "임시 비밀번호: " + tempPassword + "\n\n"
//                    + "로그인 후 즉시 비밀번호를 변경해주세요.";
//            emailService.sendSimpleMessage(emp.getEmail(), subject, text);
//        } catch(Exception e) {
//            e.printStackTrace(); // 이메일 실패 시 로그 처리, DB 롤백 방지
//        }
//    }
//
//    private String generateRandomPassword() {
//        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
//    }
//}
