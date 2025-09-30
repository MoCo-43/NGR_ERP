package com.yedam.erp.mapper.hr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yedam.erp.vo.hr.DeptPayrollSumVO;

@Mapper
public interface DeptPayrollSumMapper {
    // 등록
    int insertDeptPayrollSum(DeptPayrollSumVO vo);

    // 수정
    int updateDeptPayrollSum(DeptPayrollSumVO vo);

    // 단건 조회
    DeptPayrollSumVO selectDeptPayrollSum(Long confirmNo);

    // 연월 + 회사코드 기준 조회
    List<DeptPayrollSumVO> selectDeptPayrollSumList(String yearMonth, Long companyCode);
}
