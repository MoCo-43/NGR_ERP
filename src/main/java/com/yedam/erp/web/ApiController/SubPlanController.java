package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.SubPlanService;
import com.yedam.erp.vo.main.SubPlanVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubPlanController {
	final private SubPlanService service;
	
	@GetMapping("/subscribes")
	public List<SubPlanVO> subscribe(){
		return service.selectSubPlan();
	}
}
