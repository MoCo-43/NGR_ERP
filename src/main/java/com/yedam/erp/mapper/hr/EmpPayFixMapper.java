package com.yedam.erp.mapper.hr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yedam.erp.vo.hr.EmpPayFixVO;

@Mapper
public interface EmpPayFixMapper {

    //  수당 목록 조회 
    List<EmpPayFixVO> selectAllowList(
        @Param("empId") String empId,
        @Param("companyCode") Long companyCode
    );

    // 공제 목록 조회 
    List<EmpPayFixVO> selectDeductList(
        @Param("empId") String empId,
        @Param("companyCode") Long companyCode
    );

    // 사원 수당/공제 등록 
    int insertEmpPayFix(EmpPayFixVO vo);

    // 사원 수당/공제 수정 
    int updateEmpPayFix(EmpPayFixVO vo);
}
