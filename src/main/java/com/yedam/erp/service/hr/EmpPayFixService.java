package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpPayFixVO;

public interface EmpPayFixService {

    // 활성 수당 목록 조회
    List<EmpPayFixVO> getAllowList(String empId, Long companyCode);

    // 사원 수당 등록
    int insertEmpPayFix(EmpPayFixVO vo);

    // 사원 수당 수정
    int updateEmpPayFix(EmpPayFixVO vo);
}
