package com.yedam.erp.vo.stock;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date dueDate;
	private String vatType;
	private String taxType;
	private Long stock;
	private String xpCode;
	private String companyCode;
	
}
