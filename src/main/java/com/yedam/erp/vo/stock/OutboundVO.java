package com.yedam.erp.vo.stock;

import lombok.Data;

@Data
public class OutboundVO {

	private Long outboundNo;
	private String productCode;
	private String productName;
	private String specification;
	private Long qty;
	private String outbHeaderCode;
	private String companyCode;
	
}
