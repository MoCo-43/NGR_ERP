package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderPlanVO {
	private String xpCode;
	private String productCode;
	private String productName;
	private String specification;
	private String businessCode;
	private String businessPartner;
	private String empId;
	private Date insDate;
	private int amount;
	private int purchasePrice;
	private int supAmt;
	private String taxType;
	
	private List<OrderPlanDetailVO> details; // <- 여기에 디테일 담기
	
}
