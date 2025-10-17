package com.yedam.erp.service.impl.account;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.account.ProfitStatementMapper;
import com.yedam.erp.service.account.ProfitStatementService;
import com.yedam.erp.vo.account.ProfitStatementVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfitStatementServiceImpl implements ProfitStatementService {

    private final ProfitStatementMapper mapper;

    @Override
    public List<ProfitStatementVO> getMonthlyProfit(Map<String, Object> param) {
        return mapper.selectMonthlyProfit(param);
    }
    
    @Override
    public void upsertMonthlyNetProfit(Map<String, Object> param) {
        int updated = mapper.updateMonthlyNetProfit(param);

        // ✅ 업데이트된 행이 없다면 새로 insert
        if (updated == 0) {
            mapper.insertMonthlyNetProfit(param);
        }
    }
    
    
}