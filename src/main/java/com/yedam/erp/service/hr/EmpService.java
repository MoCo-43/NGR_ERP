package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.EmpVO;

public interface EmpService {

    // 리스트 조회
    List<EmpVO> getEmpList(EmpVO empVO);

    // 단건 조회 (emp_id 필요)
    EmpVO getEmp(EmpVO empVO);

    // 등록 (EMP_ID는 트리거에서 자동생성)
    int insertEmp(EmpVO empVO);

    // 수정 (WHERE emp_id)
    int updateEmp(EmpVO empVO);
    
    //팀장 조회
    List<EmpVO> getManagers(EmpVO empVO);
    
}