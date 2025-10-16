package com.yedam.erp.vo.stock;

import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
public class OrderPlanDetailVO {

	private Long xdNo;
	private String productCode;
	private String productName;
	private String specification;
	private String businessPartner;
	private String businessCode;
	@NumberFormat(pattern = "#,###")
	private int amount;
	@NumberFormat(pattern = "#,###")
	private int purchasePrice;
	@NumberFormat(pattern = "#,###")
	private int supAmt;
	@NumberFormat(pattern = "#,###")
	private int vatAmt;
	private String xpCode;
	private String vatType;
	
	
	
	
}
