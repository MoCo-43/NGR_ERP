package com.yedam.erp.vo.account;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PayrollJournalVO {
	    private Long histCode;
	    private String HistNo;
	    private Long payrollNo;
	    private Long yearMonth;
	    private Date payDate;
	    private Double totalDebit;
	    private Double totalCredit;
	    private Date createdAt;
	    private String createdBy;
	    private Long companyCode;

	    // ✅ 라인 리스트 포함
	    private List<PayrollJournalLineVO> lines;
}
