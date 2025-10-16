package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.DashBoardMapper;
import com.yedam.erp.service.DashBoardService;
import com.yedam.erp.vo.common.DashBoardVO;

@Service
public class DashboardServiceImpl implements DashBoardService {

	@Autowired
	private DashBoardMapper dashboardMapper;
	@Override
	public List<DashBoardVO> selectActiveMembersByDepartment() {
		return dashboardMapper.selectActiveMembersByDepartment();
	}

}
