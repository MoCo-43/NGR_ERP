package com.yedam.erp.service;

import java.util.List;
import com.yedam.erp.vo.hr.DeptVO;

public interface DeptService {
	List<DeptVO> getDeptList();

	// 등록
	void addDept(DeptVO vo);

	// 수정
	void editDept(DeptVO vo);

	// 삭제
	void removeDept(String deptCode);
}
