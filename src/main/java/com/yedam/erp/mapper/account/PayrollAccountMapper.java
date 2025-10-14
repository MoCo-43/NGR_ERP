package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.account.PayrollJournalLineVO;
import com.yedam.erp.vo.account.PayrollJournalVO;
import com.yedam.erp.vo.account.PayrollLineVO;

public interface PayrollAccountMapper {
	 List<PayrollLineVO> selectDeptJournalLines(
			 @Param("payrollNo") int payrollNo,
		        @Param("companyCode") Long companyCode
		    );
	 
	 String getNextPayrollNo();
	 
	 void insertPayrollHist(PayrollJournalVO vo);

	 void insertPayrollJrnHist(PayrollJournalLineVO vo);
	 
	 List<PayrollJournalLineVO> selectDeptJournalLines(Long payrollNo, String companyCode);

	void updatePayrollJournalInfo(PayrollJournalVO vo);
}
