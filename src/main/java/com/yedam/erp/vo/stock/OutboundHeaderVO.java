package com.yedam.erp.vo.stock;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OutboundHeaderVO {

	private String outbHeaderCode;
	private String doCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") // 이렇게 변환해도 시간정보 포함 00:00:00
	private Date dueDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") // 이렇게 변환해도 시간정보 포함 00:00:00
	private Date outboundDate;
	private String businessCode;
	private String businessPartner;
	private String companyCode;
	
	private List<OutboundVO> details;
	 
	// 거래명세서 존재 여부
	private String hasSheet;
	
	
	// 조회용 추가 필드
	private String productName;
	private Long qty;
	
	
}
