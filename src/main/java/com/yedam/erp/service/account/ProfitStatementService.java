package com.yedam.erp.service.account;

import java.util.List;
import java.util.Map;

import com.yedam.erp.vo.account.ProfitStatementVO;

public interface ProfitStatementService {
    List<ProfitStatementVO> getMonthlyProfit(Map<String, Object> param);
    
    // 당기순손익 insert or update
    void upsertMonthlyNetProfit(Map<String, Object> param);
}