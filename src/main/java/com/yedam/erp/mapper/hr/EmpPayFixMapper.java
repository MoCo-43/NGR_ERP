package com.yedam.erp.mapper.hr;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yedam.erp.vo.hr.EmpPayFixVO;

@Mapper
public interface EmpPayFixMapper {

    // 활성 수당 목록 조회 
    List<EmpPayFixVO> selectAllowList(
        @Param("empId") String empId,
        @Param("companyCode") Long companyCode
    );

    // 사원 수당 등록 (INSERT)
    int insertEmpPayFix(EmpPayFixVO vo);

    // 사원 수당 수정 (UPDATE)
    int updateEmpPayFix(EmpPayFixVO vo);
}
