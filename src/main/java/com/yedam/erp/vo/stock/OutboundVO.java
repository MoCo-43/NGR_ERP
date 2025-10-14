package com.yedam.erp.vo.stock;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	
	
	// 조회용 필드
	private String businessPartner;
	private String businessCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") // 이렇게 변환해도 시간정보 포함 00:00:00
	private Date dueDate;
	private String doCode;
	private BigDecimal vatAmt;
	private BigDecimal supAmt;
	private BigDecimal orderPrice;
	private String taxType;

	
}
