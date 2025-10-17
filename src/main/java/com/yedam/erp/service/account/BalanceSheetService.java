package com.yedam.erp.service.account;

import java.util.List;
import java.util.Map;

import com.yedam.erp.vo.account.BalanceSheetVO;

public interface BalanceSheetService {
	 List<BalanceSheetVO> selectBalanceSheet(Map<String, Object> param);
}