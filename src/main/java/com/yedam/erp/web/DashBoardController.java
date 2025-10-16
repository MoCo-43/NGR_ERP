package com.yedam.erp.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.DashBoardService;
import com.yedam.erp.vo.common.DashBoardVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class DashBoardController {

	private final DashBoardService dashBoardService;
	
	//부서별 인원수
	@GetMapping("/empmembers")
	public List<DashBoardVO> getActiveMembersData() {
		//DB조회
		List<DashBoardVO> dataList = dashBoardService.selectActiveMembersByDepartment();
		return dataList;
	}

}
