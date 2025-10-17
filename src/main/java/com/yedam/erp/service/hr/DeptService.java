package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.DeptVO;

public interface DeptService {
	// 목록
	List<DeptVO> getDeptList(Long companyCode);

	// 단건
	DeptVO getDept(String dept_code);

	// 등록
	int addDept(DeptVO vo);
	//수정
	int editDept(DeptVO vo);

}
