package com.yedam.erp.service.impl.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.account.BalanceSheetMapper;
import com.yedam.erp.service.account.BalanceSheetService;
import com.yedam.erp.vo.account.BalanceSheetVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceSheetServiceImpl implements BalanceSheetService {

	private final BalanceSheetMapper balanceSheetMapper;

	  @Override
	    public List<BalanceSheetVO> selectBalanceSheet(Map<String, Object> param) {
	        return balanceSheetMapper.selectBalanceSheet(param);
	    }
}