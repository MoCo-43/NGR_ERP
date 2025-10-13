package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OutboundHeaderVO {

	private String outbHeaderCode;
	private Long doNo;
	private Date dueDate;
	private Date outboundDate;
	private String businessCode;
	private String businessPartner;
	private String companyCode;
	
	 private List<OutboundVO> details;
}
