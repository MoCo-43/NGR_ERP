package com.yedam.erp.web.ApiController.main;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.SubscriptionService;
import com.yedam.erp.vo.account.accountVO;
import com.yedam.erp.vo.main.SubPayVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MypageController {
	private final SubscriptionService Subservice;
	
	
//	@GetMapping("/subList")
//	public List<SubPayVO>  getMethodName(@RequestParam String param) {
//		return null;
//	}
	
}
