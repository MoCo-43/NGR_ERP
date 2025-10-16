package com.yedam.erp.vo.common;

import com.yedam.erp.vo.hr.DeptVO;
import com.yedam.erp.vo.hr.EmpVO;

import lombok.Data;

@Data
public class DashBoardVO {
	private String deptName;       // 부서 이름 (T1.dept_name)
    private int memberCount;     // 활성 인원수 (COUNT(T2.dept_code))	
	private EmpVO empVO;
	private DeptVO deptVO;
}
