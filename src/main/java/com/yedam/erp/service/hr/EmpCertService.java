package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpCertVO;

public interface EmpCertService {
	//전체 조회
	List<EmpCertVO> selectEmpCertList(EmpCertVO vo);
	//등록
	boolean insertEmpCert(EmpCertVO vo);
	//삭제
	boolean deleteEmpCert(EmpCertVO vo);
}
