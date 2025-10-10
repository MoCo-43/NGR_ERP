package com.yedam.erp.vo.stock;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class InboundVO {
	
	private String  lotCode; // LOT
	private String productName; // 제품명
	private String specification; // 규격
	private Long qty; // 입고수량
	
	// timestamp 타입 시간까지 정상출력
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date inboundDate; // 입고일시
	
	// 시간 제외 출력 (오라클이 시분초까지 Date 타입인데도 저장)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date dueDate; // 납기일자
	private String note; // 비고
	private String businessPartner; // 거래처명
	private String companyCode; // 회사코드
	private String businessCode; // 거래처코드
	private String productCode; // 제품코드
	private Long purchasePrice; // 제품 단가
	private String orderCode; // 발주 코드
}
