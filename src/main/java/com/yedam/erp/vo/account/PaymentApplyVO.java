package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class PaymentApplyVO {
    private Long applyNo;       // 시퀀스
    private String type;        // S:매출 / P:매입
    private String invoiceNo;     // 매입매출 전표 FK
    private Long applyAmount;   // 적용금액
    private Long payCode;       // PAYMENT 헤더 FK
}