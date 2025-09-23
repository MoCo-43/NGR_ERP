package com.yedam.erp.vo.account;

import java.util.Date;

import lombok.Data;

@Data
public class invoiceVO {

		private String invoiceCode;   // 전표 코드 (PK)
	    private String invoiceNo;     // 전표 번호
	    private String type;          // S=매출, P=매입
	    private Date invoiceDate;     // 전표일자
	    private String orderCode;     // 주문서 코드
	    private String deptCode;      // 부서 코드
	    private String cusCode;       // 거래처 코드
	    private String bizNo;         // 사업자번호
	    private String cusName;       // 거래처명
	    private String vatType;       // 부가세 유형 (과세/영세/면세)
	    private Date dueDate;         // 결제 예정일
	    private Long subtotalAmt;     // 공급가액
	    private Long vatAmt;          // 부가세
	    private Long totalAmt;        // 합계
	    private String payMethod;     // 결제방식 (현금/카드/이체 등)
	    private String memo;          // 메모
	    private String arApAcct;      // 매출/매입 계정
	    private String cashAccount;   // 압출금 계좌
	    private String status;        // 상태 (OPEN/CLOSED 등)
	    private Date createdAt;       // 생성일
	    private Date updatedAt;       // 수정일
}
