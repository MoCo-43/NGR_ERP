package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class BalanceSheetVO {
	  private Long companyCode;   // 회사코드

	    // 회계 분류 구조 (관/항/목)
	   private int acctKwan;       // 관 (1=자산, 2=부채, 3=자본)
	    private String acctName;    // 표준계정명 (STD_ACCT_NAME)
	    private Double amount;      // 금액 (집계

}