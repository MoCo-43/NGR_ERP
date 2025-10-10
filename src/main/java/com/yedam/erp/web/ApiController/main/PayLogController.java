package com.yedam.erp.web.ApiController.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.PayLogService;
import com.yedam.erp.vo.main.PayLogVO;

@RestController
public class PayLogController {

	@Autowired
	private PayLogService payLogService;
	
    @GetMapping("/payLogs")
    public ResponseEntity<List<PayLogVO>> payLogList(@RequestParam String subCode) {
        List<PayLogVO> list = payLogService.payLogList(subCode);
        return ResponseEntity.ok(list);
    }
}
