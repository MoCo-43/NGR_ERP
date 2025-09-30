package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.DeptPayrollSumVO;

public interface DeptPayrollSumService {
	int insertDeptPayrollSum(DeptPayrollSumVO vo); // 등록

	int updateDeptPayrollSum(DeptPayrollSumVO vo); // 수정

	DeptPayrollSumVO getDeptPayrollSum(Long confirmNo); // 단건 조회

	List<DeptPayrollSumVO> getDeptPayrollSumList(String yearMonth, Long companyCode); // 연월+회사코드
}
