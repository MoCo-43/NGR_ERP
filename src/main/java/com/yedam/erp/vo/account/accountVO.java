package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class accountVO {
	
	private Long accountId;
	String acctCode;
	String acctName;	
	String stdAcctName; 	
	String category; 	
	String useYn; 	
	int acctKwan;	
	int acctHang;	
	int acctMok; 
	Long companyCode;
}
