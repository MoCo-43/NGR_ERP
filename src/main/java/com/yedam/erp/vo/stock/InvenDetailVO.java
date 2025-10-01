package com.yedam.erp.vo.stock;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class InvenDetailVO {
	
	
	private String finalSign;
	private String empSign;
	private Date insDate;
	private String empId;
	private String deptName;
	private String productCode;
	private String productName;
	private Long openingStock;
	private Long stock;
	private BigDecimal valuationAmount;
	private String icCode;
	private String companyCode;
	
	
}
