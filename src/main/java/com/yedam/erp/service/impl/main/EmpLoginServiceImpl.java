package com.yedam.erp.service.impl.main;

import java.util.List;
import java.util.UUID;

// 필요시 StringUtils 임포트
import org.springframework.util.StringUtils; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.main.EmpLoginMapper;
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

    // --- 1. activateDefaultLogin 수정 ---
    @Override
    @Transactional
    public void activateDefaultLogin(String empId) {
        // [수정됨] activateLogin 호출 시 기본 권한 "ROLE_USER" 전달
        activateLogin(empId, empId, "ROLE_USER"); 
    }

    // --- 2. 기존 activateCustomLogin(String empId) 제거 ---
    // [제거됨] 이 메소드는 activateCustomLoginWithRole로 대체되었습니다.

    @Override
    @Transactional
    public void changePassword(String empId, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        // empPw와 mustChangePw를 업데이트한다고 가정
        empLoginMapper.updatePasswordAndFlag(empId, encodedPassword, "N"); 
    }

    // --- 3. insertNewEmployeeLogin 검토/수정 ---
    @Override
    @Transactional
    public void insertNewEmployeeLogin(EmpLoginVO empLoginVO) {
        // 이 메소드가 호출될 때 어떤 권한을 부여해야 하는지 명확해야 합니다.
        // 현재로서는 VO에 comName이 없으면 기본 'ROLE_USER'를 부여하도록 수정합니다.
        
        // VO에 담긴 empNo로 EmpVO 정보 조회
        EmpVO emp = empLoginMapper.findEmployeeById(String.valueOf(empLoginVO.getEmpNo())); 
        if(emp == null) {
            throw new RuntimeException("사원 정보를 찾을 수 없습니다: " + empLoginVO.getEmpNo());
        }

        // matNo 설정 (EmpVO에 companyCode가 있다고 가정)
        if(empLoginVO.getMatNo() == null && emp.getCompanyCode() != null) {
            empLoginVO.setMatNo(emp.getCompanyCode());
        } else if (empLoginVO.getMatNo() == null) {
             throw new RuntimeException("회사 코드(matNo)를 찾을 수 없습니다.");
        }
        
        // 부여할 권한 결정
        String roleNameToAssign = empLoginVO.getComName(); // VO에 권한이 있는지 확인
        if (roleNameToAssign == null || roleNameToAssign.trim().isEmpty()) {
             roleNameToAssign = "ROLE_USER"; // 없으면 기본 'ROLE_USER' 할당
             // 필요시 경고 로그 출력
             System.err.println("주의: insertNewEmployeeLogin 호출 시 권한이 지정되지 않아 ROLE_USER를 기본 부여합니다. empNo: " + empLoginVO.getEmpNo());
        }

        // 사용할 로그인 ID (VO에서 가져옴)
        String loginIdToUse = empLoginVO.getEmpId();
        if (loginIdToUse == null || loginIdToUse.trim().isEmpty()) {
             throw new RuntimeException("로그인 ID(empId)가 VO에 없습니다.");
        }

        // 수정된 activateLogin 호출 (권한 명시적 전달)
        // activateLogin 내부에서 다시 emp 정보를 찾으므로, 여기서는 emp.getEmp_id() 전달
        activateLogin(emp.getEmp_id(), loginIdToUse, roleNameToAssign);
    }

    @Override
    public EmpVO findEmployeeById(String empId) {
        // 중요: EmpLoginMapper.xml의 findEmployeeById 쿼리가
        // emp_id, name, email, company_code 등을 포함하는지 확인하세요.
        return empLoginMapper.findEmployeeById(empId);
    }

    // --- 4. activateLogin 메소드 시그니처 및 내부 로직 수정 ---
    /**
     * [수정됨] 특정 권한(Role)으로 로그인 계정을 활성화(생성)합니다.
     * @param employeeId EmpVO 상세 정보를 조회하기 위한 내부 직원 ID (예: "EMP-2025-010")
     * @param loginId 사용자가 실제로 로그인할 ID (예: "NGR25010" 또는 "EMP-2025-010")
     * @param roleName 할당할 권한명 (예: "ROLE_USER", "ROLE_MANAGER")
     */
    private void activateLogin(String employeeId, String loginId, String roleName) {
        // employeeId로 EmpVO 조회
        EmpVO emp = empLoginMapper.findEmployeeById(employeeId);
         if (emp == null) {
             // employeeId로 조회 실패 시 loginId로 재시도 (선택적 예외처리)
             emp = empLoginMapper.findEmployeeById(loginId); 
             if (emp == null) throw new RuntimeException("사원 정보를 찾을 수 없습니다: " + employeeId + " 또는 " + loginId);
             // loginId로 찾았다면, 이후 로직의 일관성을 위해 employeeId를 loginId로 간주
             employeeId = loginId; 
        }

        // 임시 비밀번호 생성 및 암호화
        String tempPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(tempPassword);

        // DB 저장을 위한 EmpLoginVO 준비
        EmpLoginVO loginVO = new EmpLoginVO();
        loginVO.setEmpId(loginId); // 실제 로그인 ID 설정

        // employeeId ("EMP-YYYY-NNN" 형식 가정)에서 empNo 추출
         String numericPart = employeeId.replaceAll("[^0-9-]", ""); // 숫자와 하이픈만 남김
         String[] partsForEmpNo = numericPart.split("-");
         // 하이픈이 2개 이상이면 마지막 부분을, 아니면 숫자 전체를 empNo로 간주
         String empNoStr = (partsForEmpNo.length > 2) ? partsForEmpNo[2] : numericPart.replaceAll("[^0-9]", ""); 

        if (empNoStr.isEmpty()) {
            // "EMP-YYYY-NNN" 형식이 아닌 경우 empId 전체를 empNo로 사용 시도 (오류 발생 가능성 있음)
            empNoStr = employeeId.replaceAll("[^0-9]","");
            if (empNoStr.isEmpty()){
                 throw new RuntimeException("Employee ID에서 사원 번호 부분을 추출할 수 없습니다: " + employeeId);
            }
             System.err.println("Warning: Could not parse empNo from employeeId format. Using numeric parts: " + empNoStr);
        }
        try {
            loginVO.setEmpNo(Long.parseLong(empNoStr));
        } catch (NumberFormatException e) {
             throw new RuntimeException("Employee ID에서 추출된 사원 번호가 숫자가 아닙니다: " + empNoStr);
        }


        loginVO.setEmpPw(encodedPassword);
        loginVO.setMustChangePw("Y");

        // matNo 설정 (EmpVO의 companyCode 필드 사용)
        if (emp.getCompanyCode() != null) {
            loginVO.setMatNo(emp.getCompanyCode());
        } else {
             // companyCode가 꼭 필요한 경우 오류 발생
              throw new RuntimeException("사원 정보에 회사 코드(companyCode)가 없습니다: " + employeeId);
        }

        // [수정됨] 파라미터로 전달받은 roleName 설정
        loginVO.setComName(roleName); 

        // DB에 INSERT
        empLoginMapper.insertNewEmployeeLogin(loginVO);

        // 안내 이메일 발송
        sendInitialPasswordEmail(emp, loginId, tempPassword);
    }

    // --- 나머지 헬퍼 메소드 (sendInitialPasswordEmail, generateRandomPassword) ---
    private void sendInitialPasswordEmail(EmpVO emp, String loginId, String tempPassword) {
         try {
             String subject = "[NGR_ERP 시스템] 계정 안내";
             String text = "안녕하세요, " + (emp != null ? emp.getName() : "고객") + "님.\n\n" // emp 객체 null 체크 추가
                     + "로그인 ID: " + loginId + "\n"
                     + "임시 비밀번호: " + tempPassword + "\n\n"
                     + "로그인 후 즉시 비밀번호를 변경해주시길 바랍니다.";
             // emp 객체 및 이메일 주소 유효성 검사 추가
             if (emp != null && emp.getEmail() != null && !emp.getEmail().trim().isEmpty()) {
                 emailService.sendSimpleMessage(emp.getEmail(), subject, text);
             } else {
                  // 이메일 발송 불가 시 경고 로그 (롤백 방지)
                  System.err.println("주의: 초기 비밀번호 안내 이메일을 발송할 수 없습니다. 사원 정보 또는 이메일 주소가 누락되었습니다. loginId: " + loginId);
             }
         } catch(Exception e) {
             // 이메일 발송 실패 시에도 롤백되지 않도록 예외 처리 및 로그 기록
             e.printStackTrace(); 
             System.err.println("오류: 초기 비밀번호 안내 이메일 발송 실패. loginId: " + loginId + ", Error: " + e.getMessage());
         }
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    // --- 기타 기존 메소드들 ---
    @Override
    public EmpVO mypageInfo(String empId) {
        return empLoginMapper.mypageInfo(empId);
    }

    // 이름이 다른 중복 메소드 (findMatNoByEmpId 사용 권장)
    @Override
    public Long findMatNOByEmpId(String empId) { 
        return empLoginMapper.findMatNoByEmpId(empId);
    }
    
    // TODO 주석이 있거나 null을 반환하는 중복 메소드 (정리 필요)
    @Override
    public Long findMatNoByEmpId(String empId) { 
        return empLoginMapper.findMatNoByEmpId(empId); // 매퍼 호출하도록 수정 (또는 이 메소드 자체를 삭제)
    }

    @Override
    public boolean idChecks(String empId) {
        return empLoginMapper.idChecks(empId) > 0;
    }

    // --- 권한 변경 메소드 (구현 정확함) ---
    @Override
    @Transactional 
    public void updateEmployeeRole(String empId, String newRoleName) {
        EmpLoginVO loginVO = new EmpLoginVO();
        loginVO.setEmpId(empId);
        loginVO.setComName(newRoleName); 
        empLoginMapper.updateEmployeeRole(loginVO);
    }

    // --- 관리자가 권한 선택하여 계정 생성하는 메소드 (구현 정확함) ---
    @Override
    @Transactional
    public void activateCustomLoginWithRole(List<String> empIds, String roleName) {
        for (String empId : empIds) {
            String customLoginId;
            try {
                // empId 형식 검사 강화
                if (empId == null || !empId.matches("EMP-\\d{4}-\\d+")) {
                     throw new IllegalArgumentException("empId 형식이 올바르지 않습니다.");
                }
                String[] parts = empId.split("-");
                 // parts 배열 길이 및 각 부분 길이 검사 추가
                if (parts.length < 3 || parts[1].length() < 4) { 
                     throw new IllegalArgumentException("empId 구조가 올바르지 않습니다.");
                }
                String yearLastTwoDigits = parts[1].substring(2);
                String numberPart = parts[2];
                customLoginId = "NGR" + yearLastTwoDigits + numberPart;
            } catch (Exception e) {
                // 개별 실패 시 로그 남기고 다음 ID 처리 계속
                System.err.println("empId 처리 오류 (건너뜀): " + empId + " - " + e.getMessage());
                continue; 
            }
            
            // 수정된 activateLogin 호출 (권한 명시적 전달)
            activateLogin(empId, customLoginId, roleName);
        }
    }

	@Override
	public void activateCustomLogin(String empId) {
		// TODO Auto-generated method stub
		
	}
}