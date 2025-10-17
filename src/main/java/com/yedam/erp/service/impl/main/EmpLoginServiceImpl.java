package com.yedam.erp.service.impl.main;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.main.EmpLoginMapper;
import com.yedam.erp.service.hr.EmpService;
import com.yedam.erp.service.main.EmailService;
import com.yedam.erp.service.main.EmpLoginService;
import com.yedam.erp.vo.hr.EmpVO;
import com.yedam.erp.vo.main.EmpLoginVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpLoginServiceImpl implements EmpLoginService {

    private final EmpLoginMapper empLoginMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public List<EmpVO> findEmployeesByDept(String deptName, String title) {
        return empLoginMapper.findEmployeesByDept(deptName, title);
    }

    @Override
    @Transactional
    public void activateDefaultLogin(String empId) {
        activateLogin(empId, empId);
    }

    @Override
    @Transactional
    public void activateCustomLogin(String empId) { 
        // empId가 "EMP-2025-010"과 같은 형식이라고 가정합니다.
        
        try {
            // 1. empId를 "-" 기준으로 분리합니다.
            //    예: "EMP-2025-010" -> ["EMP", "2025", "010"]
            String[] parts = empId.split("-");
            
            // 2. 두 번째 부분(연도)에서 뒤의 두 자리만 가져옵니다.
            //    예: "2025" -> "25"
            String yearLastTwoDigits = parts[1].substring(2);
            
            // 3. 세 번째 부분을 가져옵니다.
            //    예: "010"
            String numberPart = parts[2];
            
            // 4. "NGR"과 조합하여 최종 ID를 만듭니다.
            //    예: "NGR" + "25" + "010" -> "NGR25010"
            String customLoginId = "NGR" + yearLastTwoDigits + numberPart;
            
            // 5. 생성된 customLoginId로 activateLogin 메소드를 호출합니다.
            activateLogin(empId, customLoginId);

        } catch (Exception e) {
            // "EMP-YYYY-NNN" 형식이 아닐 경우 예외가 발생할 수 있습니다.
            // (예: parts[1]이나 parts[2]가 없는 경우 등)
            // 실제 운영 코드에서는 이 부분에 대한 예외 처리를 해주시는 것이 좋습니다.
            System.err.println("empId 형식 오류: " + empId);
            // e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void changePassword(String empId, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        empLoginMapper.updatePasswordAndFlag(empId, encodedPassword, "N");
    }

    @Override
    @Transactional
    public void insertNewEmployeeLogin(EmpLoginVO empLoginVO) {
        // 관리자 계정 생성도 activateLogin으로 통합
        EmpVO emp = empLoginMapper.findEmployeeById(String.valueOf(empLoginVO.getEmpNo()));
        if(emp == null) {
            throw new RuntimeException("사원 정보를 찾을 수 없습니다: " + empLoginVO.getEmpNo());
        }

        // matNo가 VO에 없으면 emp에서 가져오기
        if(empLoginVO.getMatNo() == null) {
            empLoginVO.setMatNo(emp.getCompanyCode());
        }

        // activateLogin 재사용
        activateLogin(String.valueOf(empLoginVO.getEmpNo()), empLoginVO.getEmpId());
    }

    @Override
    public EmpVO findEmployeeById(String empId) {
        return empLoginMapper.findEmployeeById(empId);
    }

    
    private void activateLogin(String employeeId, String loginId) {
        EmpVO emp = empLoginMapper.findEmployeeById(employeeId);
        if(emp == null) throw new RuntimeException("사원 정보를 찾을 수 없습니다: " + employeeId);

        // 임시 비밀번호 생성 및 암호화
        String tempPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(tempPassword);

        EmpLoginVO loginVO = new EmpLoginVO();
        //loginVO.setEmpId(loginId);
        loginVO.setEmpId(emp.getEmp_id());

        // 숫자만 추출해서 Long 변환
        String numericPart = employeeId.replaceAll("[^0-9]", "");
        if(numericPart.isEmpty()) {
            throw new RuntimeException("Employee ID에서 숫자를 추출할 수 없습니다: " + employeeId);
        }
        loginVO.setEmpNo(Long.parseLong(numericPart));

        loginVO.setEmpPw(encodedPassword);
        loginVO.setMustChangePw("Y");
        loginVO.setMatNo(emp.getCompanyCode());

        empLoginMapper.insertNewEmployeeLogin(loginVO);
        sendInitialPasswordEmail(emp, loginId, tempPassword);
    }

    private void sendInitialPasswordEmail(EmpVO emp, String loginId, String tempPassword) {
        try {
            String subject = "[NGR_ERP 시스템] 계정 안내";
            String text = "안녕하세요, " + emp.getName() + "님.\n\n"
                    + "로그인 ID: " + loginId + "\n"
                    + "임시 비밀번호: " + tempPassword + "\n\n"
                    + "로그인 후 즉시 비밀번호를 변경해주시길 바랍니다.";
            emailService.sendSimpleMessage(emp.getEmail(), subject, text);
        } catch(Exception e) {
            e.printStackTrace(); // 이메일 실패 시 로그 처리, DB 롤백 방지
        }
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

	@Override
	public EmpVO mypageInfo(String empId) {
		return empLoginMapper.mypageInfo(empId);
	}

	@Override
	public Long findMatNOByEmpId(String empId) {
		return empLoginMapper.findMatNoByEmpId(empId);
	}

	@Override
	public Long findMatNoByEmpId(String empId) {
		// TODO Auto-generated method stub
		return null;
	}
	//아이디 중복체크
	@Override
	public boolean idChecks(String empId) {
	    return empLoginMapper.idChecks(empId) > 0;
	}


}
