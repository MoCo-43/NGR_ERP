package com.yedam.erp.web.ApiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.ProfitStatementService;
import com.yedam.erp.vo.account.ProfitStatementVO;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class ProfitStatementRestController {
	  private final ProfitStatementService profitService;

	    @GetMapping("/profit-statement-dash")
	    public List<ProfitStatementVO> getProfitStatement(
	            @RequestParam String curYm,
	            @RequestParam String prevYm
	    ) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("curYm", curYm);
	        param.put("prevYm", prevYm);
	        param.put("companyCode", SessionUtil.companyId()); // ✅ 추가
	        System.out.println("🟢 [DASH 요청 파라미터] " + param);
	        List<ProfitStatementVO> list = profitService.getMonthlyProfit(param);
	        System.out.println("🟢 [조회 결과] " + list);
	        return profitService.getMonthlyProfit(param);
	    }
}
