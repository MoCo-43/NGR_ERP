package com.yedam.erp.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;

@Mapper
public interface PayrollMapper {

    // 전체조회
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

    // 수당 컬럼 LIST
    String selectAllowColList(PayrollVO vo);

    // 공제 컬럼 LIST
    String selectDeductColList(PayrollVO vo);

    // 사원별 상세
    List<Map<String,Object>> selectPayrollDetailPivot(Map<String,Object> param);

    // 부서합계
    Map<String,Object> selectDeptSum(PayrollVO vo);

    // 공제 저장
    int upsertDeduct(PayrollSummaryVO vo);
}
