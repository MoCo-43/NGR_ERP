package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.EmpVO;

public interface EmpService {
	// 전체조회 
	List<EmpVO> getEmpList(Long companyCode);
	// 단건조회
	EmpVO getEmp(EmpVO empVO);
	// 등록
	int insertEmp(EmpVO empVO);
	//수정
	int updateEmp(EmpVO empVO);
	//팀장만 조회
	List<EmpVO> getManagers(Long companyCode);
}
