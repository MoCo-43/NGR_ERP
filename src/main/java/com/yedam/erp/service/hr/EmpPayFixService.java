package com.yedam.erp.service.hr;

import com.yedam.erp.vo.hr.EmpPayFixVO;



public interface EmpPayFixService {
	EmpPayFixVO get(String empId);

	boolean insert(EmpPayFixVO vo);

	boolean update(EmpPayFixVO vo);

	boolean upsert(EmpPayFixVO vo);
}
