package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class JournalAmountSumVO {
    private Long debit;   // 차변 합계
    private Long credit;  // 대변 합계
}