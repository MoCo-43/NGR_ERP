package com.yedam.erp.service.account;
import java.util.List;

import com.yedam.erp.vo.account.PayrollLineVO;

public interface PayrollAccountService {
	List<PayrollLineVO> getDeptJournalLines(String yearMonth, String deptCode, Long companyCode);
}
