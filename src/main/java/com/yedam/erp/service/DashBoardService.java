package com.yedam.erp.service;

import java.util.List;

import com.yedam.erp.vo.common.DashBoardVO;

public interface DashBoardService {
	List<DashBoardVO> selectActiveMembersByDepartment();

}
