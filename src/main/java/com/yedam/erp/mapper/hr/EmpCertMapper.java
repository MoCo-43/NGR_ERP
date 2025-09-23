package com.yedam.erp.mapper.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpCertVO;

public interface EmpCertMapper {
	// 목록
	List<EmpCertVO> selectEmpCertList(EmpCertVO vo);

	// 등록
	int insert(EmpCertVO vo);

	// 삭제
	int deleteByPk(Long certNo);
}
