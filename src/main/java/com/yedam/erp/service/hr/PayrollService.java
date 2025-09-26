package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayrollVO;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollDeptSumVO;

public interface PayrollService {

	// 급여대장 마스터 

	// 회사별 전체 조회
	List<PayrollVO> getPayrollList(Long companyCode);

	// 단건 조회
	PayrollVO getPayroll(Long payrollNo);

	// 등록
	int addPayroll(PayrollVO vo);

	// 수정
	int editPayroll(PayrollVO vo);

	// 상태 변경 (확정/취소)
	int changePayrollStatus(PayrollVO vo);

	// 월·부서별 조회
	List<PayrollVO> getPayrollListByCond(PayrollVO vo);

	// 급여대장 상세 

	// 사원별 상세 리스트
	List<PayrollSummaryVO> getPayrollSummary(Long payrollNo);

	// 부서별 합계
	PayrollDeptSumVO getDeptSum(Long payrollNo);
}
