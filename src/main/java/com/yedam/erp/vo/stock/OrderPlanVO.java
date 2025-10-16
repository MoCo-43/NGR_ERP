package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderPlanVO {
	private String xpCode;
	private String productCode;
	private String productName;
	private String specification;
	private String businessCode;
	private String businessPartner;
	private Long companyCode;
	
	private String empId;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
	private Date insDate;
	@NumberFormat(pattern = "#,###")
	private int amount;
	@NumberFormat(pattern = "#,###")
	private int purchasePrice;
	@NumberFormat(pattern = "#,###")
	private int supAmt;
	
	private String vatType;
	
	private List<OrderPlanDetailVO> details; // <- 여기에 디테일 담기
	
	// 발주 존재 여부
    private String hasOrderSheet;
	
}
