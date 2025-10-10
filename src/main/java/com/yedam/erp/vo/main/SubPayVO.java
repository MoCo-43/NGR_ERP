package com.yedam.erp.vo.main;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class SubPayVO {
	private Long payLogNo;
	private Long amount;
	private LocalDate payDate;
	private String pgIsAuto;
	private String transactionNo;
	private String payMethod;
	private String subCode;
	private String billingKeyNo;
	private Date endPayDay;
	private Long excessPee;
	private Long reductionFee;
	private Long vatAmt;	
}
