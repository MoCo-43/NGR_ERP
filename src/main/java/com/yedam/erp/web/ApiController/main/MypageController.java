package com.yedam.erp.web.ApiController.main;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.EmpLoginService;
import com.yedam.erp.service.main.SubscriptionService;
import com.yedam.erp.vo.hr.EmpVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MypageController {
	private final SubscriptionService Subservice;
	private final EmpLoginService empLoginService;
//    @GetMapping("/mypage")
//    public String mypage(@RequestParam String empId, Model model) throws Exception {
//
//        // 1. 사원 정보 조회
//        EmpVO emp = empLoginService.mypageInfo(empId);
//        model.addAttribute("emp", emp);
//
//        // 2. 일정 데이터 조회 및 JSON 변환
////        List<EventVO> events = calendarService.getEvents(empId);
////        String eventsJson = objectMapper.writeValueAsString(events);
////        model.addAttribute("eventsJson", eventsJson);
//
//        return "main/mypage"; 
//    }	
	
//	@GetMapping("/subList")
//	public List<SubPayVO>  getMethodName(@RequestParam String param) {
//		return null;
//	}
	
}
