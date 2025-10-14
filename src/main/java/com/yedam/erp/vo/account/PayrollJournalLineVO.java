package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class PayrollJournalLineVO {
	 private Integer lineNo;
	    private String itemKey;
	    private String acctCode;
	    private String dcType;
	    private String remarks;
	    private Long histCode;
	    private Long companyCode;
}
