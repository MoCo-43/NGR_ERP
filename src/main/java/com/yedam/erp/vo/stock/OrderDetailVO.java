package com.yedam.erp.vo.stock;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDetailVO {
	private int odNo;
	private String productCode;
	private String productName;
	private String specification;
	private BigDecimal qty;
	private BigDecimal orderPrice;
	private BigDecimal supAmt;
	private BigDecimal vatAmt;
	private String note;
	private String orderCode;
	
	private String xpCode;
}
