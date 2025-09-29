package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class OrderVO {

	private String orderCode;
	private String businessPartner;
	private String productName;
	private String cusCode;
	private Long amount;
	private Long supAmt;
	private String empId;
	private String vatType;
	
	private Date dueDate;
	private String xpCode;
	
	
	private List<OrderDetailVO> details;
	
	
}
