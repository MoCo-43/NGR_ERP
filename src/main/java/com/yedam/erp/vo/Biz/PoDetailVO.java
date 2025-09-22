package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class PoDetailVO {
    private Long podNo;         // 상세주문번호 (PK)
    private Long poId;          // 주문번호 (FK)
    private String productCode; // 제품코드
    private Integer orderQty;   // 주문수량
    private Long unitPrice;     // 단가
    private Long dcPrice;       // 할인금액
    private Long supAmt;        // 공급가액
    private Long vatAmt;        // 부가세
    private String dPrdName;    // 상세 제품명
}
