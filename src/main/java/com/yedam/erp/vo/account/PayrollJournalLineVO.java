package com.yedam.erp.vo.account;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PayrollJournalLineVO {
	 private Integer lineNo;
	    private String itemKey;
	    private String acctCode;
	    private String dcType;
	    private String remarks;
	    private BigDecimal amount; 
	    private Long histCode;
	    private Long companyCode;
	   
	   
	    
}
