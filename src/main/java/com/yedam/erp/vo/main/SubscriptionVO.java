package com.yedam.erp.vo.main;

import java.util.Date;

import lombok.Data;

@Data
public class SubscriptionVO {
	private String subCode;
	private Date startDate;
	private Date endDate;
	private Long totalUsers;
	private String subStatus;
	private Long subPlanNo;
	private Long totalPay;
	private Long matNo;
	private Long mon;
	private String comCode;
	private SubPlanVO subPlan; 
}

