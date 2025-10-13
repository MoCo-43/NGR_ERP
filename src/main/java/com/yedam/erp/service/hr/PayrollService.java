package com.yedam.erp.service.hr;

import java.util.List;
import java.util.Map;

import com.yedam.erp.vo.hr.PayrollVO;
import com.yedam.erp.vo.hr.PayrollSummaryVO;

public interface PayrollService {

    // 급여대장 목록
    List<PayrollVO> selectPayrollList(PayrollVO vo);

    // 단건조회
    PayrollVO selectPayroll(PayrollVO vo);

    // 등록
    int insertPayroll(PayrollVO vo);

    // 수정
    int updatePayroll(PayrollVO vo);

    // 상태변경
    int updatePayrollStatus(PayrollVO vo);

    // 조건조회
    List<PayrollVO> selectPayrollListByCond(PayrollVO vo);

    // 수당 컬럼 목록 LISTAGG
    String selectAllowColList(PayrollVO vo);

    // 공제 컬럼 목록 LISTAGG
    String selectDeductColList(PayrollVO vo);

    // 사원별 상세 (PIVOT 결과)
    List<Map<String,Object>> selectPayrollDetailPivot(Long payrollNo, Long companyCode);

    // 부서합계
    Map<String,Object> selectDeptSum(PayrollVO vo);

    // 공제 저장
    int upsertDeduct(PayrollSummaryVO vo);
}
