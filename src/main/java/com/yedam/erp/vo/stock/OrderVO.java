package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") // 이렇게 변환해도 시간정보 포함 00:00:00
	private Date dueDate;
	private String xpCode;
	private String companyCode;
	
	private List<OrderDetailVO> details;
	
	
}
