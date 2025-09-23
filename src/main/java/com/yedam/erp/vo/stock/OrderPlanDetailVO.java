package com.yedam.erp.vo.stock;

import lombok.Data;

@Data
public class OrderPlanDetailVO {

	private Long xdNo;
	private String productCode;
	private String productName;
	private String specification;
	private String businessPartner;
	private String businessCode;
	private int amount;
	private int purchasePrice;
	private int supAmt;
	private int vatAmt;
	private String xpCode;
	
	
	
	
}
