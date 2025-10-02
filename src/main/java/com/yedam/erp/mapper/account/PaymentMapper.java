package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.account.PaymentLineVO;

public interface PaymentMapper {
	 List<PaymentLineVO> selectDeptJournalLines(
		        @Param("yearMonth") String yearMonth,
		        @Param("deptCode") String deptCode,
		        @Param("companyCode") Long companyCode
		    );
}
