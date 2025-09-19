package com.yedam.erp.web.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.erp.service.TestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class ViewTestController {

	final private TestService testService;
	
	@GetMapping("/list")
	public String list(Model model) {
		//model.addAttribute("list",testService.selectAll());
		System.out.println(testService.selectAll().toString());
		return "index";
	}
	
}
