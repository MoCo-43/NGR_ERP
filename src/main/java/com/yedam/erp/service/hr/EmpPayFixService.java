package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpPayFixVO;

public interface EmpPayFixService {

    // 수당 조회 
    List<EmpPayFixVO> getAllowList(String empId, Long companyCode);

    // 공제  조회 
    List<EmpPayFixVO> getDeductList(String empId, Long companyCode);

    // 사원 수당/공제 등록 
    int insertEmpPayFix(EmpPayFixVO vo);

    // 사원 수당/공제 수정
    int updateEmpPayFix(EmpPayFixVO vo);
}
