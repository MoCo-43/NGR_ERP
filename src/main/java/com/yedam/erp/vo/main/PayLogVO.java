package com.yedam.erp.vo.main;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class PayLogVO {
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
	private Long reductionPee;
	private Long vatAmt;
}
