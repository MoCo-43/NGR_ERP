package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpPayFixVO;

public interface EmpPayFixService {

    // 수당 목록 조회 
    List<EmpPayFixVO> getAllowList(String empId, Long companyCode);

    // 공제 목록 조회 
    List<EmpPayFixVO> getDeductList(String empId, Long companyCode);

    // 사원 수당/공제 등록 (payType 으로 구분)
    int insertEmpPayFix(EmpPayFixVO vo);

    // 사원 수당/공제 수정 (payType 으로 구분)
    int updateEmpPayFix(EmpPayFixVO vo);
}
