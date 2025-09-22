package com.yedam.erp.service;

import java.util.List;

import com.yedam.erp.vo.hr.EmpEduVO;

public interface EmpEduService {

    // 사번별 교육 리스트
    List<EmpEduVO> selectEmpEduList(EmpEduVO empEduVO);

    // 등록
    boolean insertEmpEdu(EmpEduVO empEduVO);

    // 삭제
    boolean deleteEmpEdu(EmpEduVO empEduVO);
}
