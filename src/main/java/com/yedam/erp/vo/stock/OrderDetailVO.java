package com.yedam.erp.vo.stock;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
public class OrderDetailVO {
	private int odNo;
	private String productCode;
	private String productName;
	private String specification;
	@NumberFormat(pattern = "#,###")
	private BigDecimal qty;
	@NumberFormat(pattern = "#,###")
	private BigDecimal orderPrice;
	@NumberFormat(pattern = "#,###")
	private BigDecimal supAmt;
	@NumberFormat(pattern = "#,###")
	private BigDecimal vatAmt;
	private String note;
	private String orderCode;
	private Date dueDate;
	private String taxType;
	
	private String xpCode;
	private String companyCode;
}
