package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpEduVO;

public interface EmpEduService {
	//조회
    List<EmpEduVO> selectEmpEduList(EmpEduVO vo);
    //등록
    boolean insertEmpEdu(EmpEduVO vo);
    //삭제
    boolean deleteEmpEdu(EmpEduVO vo);
}