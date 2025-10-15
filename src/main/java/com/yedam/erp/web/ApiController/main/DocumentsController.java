package com.yedam.erp.web.ApiController.main;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.DocumentService;
import com.yedam.erp.vo.main.DocumentsVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class DocumentsController {

	private final DocumentService service;
	//경로
	@PostMapping("/sign")

	public int mainSign(@RequestBody DocumentsVO vo) {
		vo.setMatNo(SessionUtil.companyId());
		return service.insertSign(vo);
	}

}
