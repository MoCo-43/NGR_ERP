package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
public class OrderVO {

	private String orderCode;
	private String businessPartner;
	private String productName;
	private String cusCode;
	@NumberFormat(pattern = "#,###")
	private Long amount;
	@NumberFormat(pattern = "#,###")
	private Long supAmt;
	private String empId;
	private String vatType;
	
	private Date dueDate;
	private String xpCode;
	
	
	private List<OrderDetailVO> details;
	
	
}
