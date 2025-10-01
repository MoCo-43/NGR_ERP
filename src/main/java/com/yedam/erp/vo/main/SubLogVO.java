package com.yedam.erp.vo.main;

import java.util.Date;

import lombok.Data;

@Data
public class SubLogVO {
	private Long subLogNo;
	private String subType;
	private Long oldSubPlan;
	private Long newSubPlan;
	private Long changePay;
	private Date startDate;
	private Date endDate;
	private Date loggedAt;
	private String subCode;
}
