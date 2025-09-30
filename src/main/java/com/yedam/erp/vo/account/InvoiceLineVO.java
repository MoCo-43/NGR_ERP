package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class InvoiceLineVO {
    private Integer lineNo;       // 전표 라인번호
    private String productName;   // 품목명
    private Long qty;             // 수량
    private Long unitPrice;       // 단가
    private Long supAmt;          // 공급가액
    private Long vatAmt;          // 부가세

    private String invoiceCode;   // 헤더 참조
    private Long companyCode;   // 회사코드
}