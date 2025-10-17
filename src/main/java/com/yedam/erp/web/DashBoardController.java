package com.yedam.erp.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.DashBoardService;
import com.yedam.erp.vo.common.DashBoardVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashBoardController {

	private final DashBoardService dashBoardService;
	
	//부서별 인원수
	@GetMapping("/empmembers")
	public List<DashBoardVO> getActiveMembersData() {
		//DB조회
		List<DashBoardVO> dataList = dashBoardService.selectActiveMembersByDepartment();
		return dataList;
	}
	
	@GetMapping("/account")
	public Map<String, Object> getProfit() {
	    Long companyCode = SessionUtil.companyId();
	    List<DashBoardVO> list = dashBoardService.selectProfitDash(companyCode);

	    Long curProfit = 0L;
	    Long prvProfit = 0L;

	    String curMonth = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMM"));
	    String prvMonth = java.time.LocalDate.now().minusMonths(1).format(java.time.format.DateTimeFormatter.ofPattern("yyyyMM"));

	    for (DashBoardVO vo : list) {
	        if (vo.getYearMonth().equals(curMonth)) {
	            curProfit = vo.getNetProfitAmt();
	        } else if (vo.getYearMonth().equals(prvMonth)) {
	            prvProfit = vo.getNetProfitAmt();
	        }
	    }

	    Map<String, Object> result = new java.util.HashMap<>();
	    result.put("profitLabel", "당기순손익");
	    result.put("curProfit", curProfit);
	    result.put("prvProfit", prvProfit);
	    return result;
	}
	
	
}
