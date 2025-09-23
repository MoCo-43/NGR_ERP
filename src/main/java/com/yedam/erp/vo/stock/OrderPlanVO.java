package com.yedam.erp.vo.stock;

import lombok.Data;

@Data
public class OrderPlanVO {

	private String productCode;
	private String productName;
	private String specification;
	private String businessCode;
	private String businessPartner;
	private int amount;
	private int purchasePrice;
	private int supAmt;
	private int taxType;
	
	
	
}
