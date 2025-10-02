package com.yedam.erp.service.account;
import java.util.List;

import com.yedam.erp.vo.account.PaymentLineVO;

public interface PaymentService {
	List<PaymentLineVO> getDeptJournalLines(String yearMonth, String deptCode, Long companyCode);
}
