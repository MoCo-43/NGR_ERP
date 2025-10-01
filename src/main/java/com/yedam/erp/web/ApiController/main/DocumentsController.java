package com.yedam.erp.web.ApiController.main;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.DocumentService;
import com.yedam.erp.service.account.AccountService;
import com.yedam.erp.service.account.JournalCloseLogService;
import com.yedam.erp.service.account.JournalService;
import com.yedam.erp.vo.main.DocumentsVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class DocumentsController {

	private final DocumentService service;
	//경로
	@PostMapping("/sign")
	public int mainSign(DocumentsVO vo) {
		vo.setMatNo(SessionUtil.companyId());
		return service.insertSign(vo);
	}

}
