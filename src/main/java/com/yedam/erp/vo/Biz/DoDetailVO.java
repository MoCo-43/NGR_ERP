package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class DoDetailVO {

    private Long dodNo;         // 출하지시 상세 번호 
    private Long doNo;          // 출하지시 번호 
    private String productCode; // 제품 코드
    private Integer qty;        // 수량
    private Integer unitPrice;  // 단가
    private Integer supAmt;     // 공급가액
    private Integer vatAmt;     // 부가세
    private Integer dcPrice;    // 할인 금액
    private Long companyCode; // 회사 코드
}