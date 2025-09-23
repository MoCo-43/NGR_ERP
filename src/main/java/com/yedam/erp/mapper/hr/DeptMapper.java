package com.yedam.erp.mapper.hr;

import java.util.List;
import com.yedam.erp.vo.hr.DeptVO;

public interface DeptMapper {
	List<DeptVO> selectDeptList();

	// 등록
	int insertDept(DeptVO vo);

	// 수정
	int updateDept(DeptVO vo);

	// 삭제
	int deleteDept(String deptCode);
}
