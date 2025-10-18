package com.yedam.erp.service.main;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.hr.EmpVO;
import com.yedam.erp.vo.main.EmpLoginVO;

public interface EmpLoginService {
    // 사원 조회
	List<EmpVO> findEmployeesByDept(@Param("deptName") String deptName, @Param("title") String title);
    // 아이디  생성 (관리자가 클릭했을 때)
    void insertNewEmployeeLogin(EmpLoginVO empLoginVO);

    // 사원 아이디 기반 로그인용 계정 생성 (관리자 생성 여부 상관 없음)
    void activateDefaultLogin(String empId);

    // 관리자 생성 계정 활성화 및 초기 비밀번호 발송
    void activateCustomLogin(String empId);

    // 비밀번호 변경
    void changePassword(String empId, String newPassword);

    // 사원 조회
    EmpVO findEmployeeById(String empId);
    
    //마이페이지 사원조회
    //List<EmpVO> mypageList(String name); 
    EmpVO mypageInfo(String empId);
    Long findMatNOByEmpId(String empId);

	Long findMatNoByEmpId(String empId);
	
	boolean idChecks(String empId);
	
	//관리자가 사원의 시스템 권한을 직접 변경
	void updateEmployeeRole(String empId, String newRoleName);
	void activateCustomLoginWithRole(List<String> empIds, String roleName);
	
}
