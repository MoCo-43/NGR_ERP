package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import com.yedam.erp.vo.account.BalanceSheetVO;

public interface BalanceSheetMapper {
	 List<BalanceSheetVO> selectBalanceSheet(Map<String, Object> param);
}