package com.yedam.erp.service;

import java.util.List;
import com.yedam.erp.vo.hr.EmpEduVO;

public interface EmpEduService {
    List<EmpEduVO> selectEmpEduList(EmpEduVO vo);
    boolean insertEmpEdu(EmpEduVO vo);
    boolean deleteEmpEdu(EmpEduVO vo);
}