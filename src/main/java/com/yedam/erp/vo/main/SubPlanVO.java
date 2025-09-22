package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class SubPlanVO {
	private Long subPlanNo;
	private String planName;
	private String planBody;
	private Long baseSalary;
	private Long minUsers;
	private Long maxUsers;
	private Long underMinFee;
	private Long excessFee;
	private String avaiModules;

}
