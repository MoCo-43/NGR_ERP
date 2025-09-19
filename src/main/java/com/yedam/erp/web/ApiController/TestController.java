package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.TestService;
import com.yedam.erp.vo.BookVO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class TestController {
	
	final private TestService service;
	
	@GetMapping("/list")
	public List<BookVO> list(){
		return service.selectAll();
	}

}
