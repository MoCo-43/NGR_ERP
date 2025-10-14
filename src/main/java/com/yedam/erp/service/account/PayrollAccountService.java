package com.yedam.erp.service.account;
import java.util.List;

import com.yedam.erp.vo.account.PayrollJournalVO;
import com.yedam.erp.vo.account.PayrollLineVO;

public interface PayrollAccountService {
	
	List<PayrollLineVO> getDeptJournalLines(int payrollNo, Long companyCode);
	
	String registerPayrollJournal(PayrollJournalVO vo);
}
