package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.PayrollVO;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollDeptSumVO;

@Mapper
public interface PayrollMapper {

	// 급여대장 마스터

	// 전체조회 (회사별)
	List<PayrollVO> selectPayrollList(Long companyCode);

    // 단건 조회
    PayrollVO selectPayroll(Long payrollNo);

    // 등록
    int insertPayroll(PayrollVO vo);

    // 수정
    int updatePayroll(PayrollVO vo);

    // 상태 변경 (확정/취소)
    int updatePayrollStatus(PayrollVO vo);

    // 월·부서별 조회
    List<PayrollVO> selectPayrollListByCond(PayrollVO vo);

    // 급여대장 상세

    // 사원별 상세 리스트
    List<PayrollSummaryVO> selectPayrollSummary(Long payrollNo);

    // 부서별 합계
    PayrollDeptSumVO selectDeptSum(Long payrollNo);
    
    //  사원 공제 저장 
    int upsertDeduct(PayrollSummaryVO vo);
    //  부서별 확정 
    int insertDeptPayrollSum(PayrollDeptSumVO vo);
}
