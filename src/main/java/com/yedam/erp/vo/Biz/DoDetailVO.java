package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class DoDetailVO {

    private String dodNo;         // 출하지시 상세 번호 
    private Long doNo;          // 출하지시 번호
    private String poCode;  // 주문서코드
    private String productCode; // 제품 코드
    private Long orderQty;        // 수량
    private Long unitPrice;  // 단가
    private Long supAmt;     // 공급가액
    private Long vatAmt;     // 부가세
    private Long dcAmt;    // 할인 금액
    private Long companyCode; // 회사 코드
}