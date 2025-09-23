package com.yedam.erp.service;

import java.util.List;
import com.yedam.erp.vo.hr.DeptVO;

public interface DeptService {
	// 목록(검색: dept_code / dept_name / use_yn)
	List<DeptVO> getDeptList(DeptVO param);

	// 단건
	DeptVO getDept(String dept_code);

	// 등록/수정/삭제
	int addDept(DeptVO vo);

	int editDept(DeptVO vo);

	int removeDept(String dept_code);
}
