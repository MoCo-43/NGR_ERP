package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import com.yedam.erp.vo.account.ProfitStatementVO;

public interface ProfitStatementMapper {
    List<ProfitStatementVO> selectMonthlyProfit(Map<String, Object> param);
    
    void insertMonthlyNetProfit(Map<String, Object> param);
    
    int updateMonthlyNetProfit(Map<String, Object> param);
}
