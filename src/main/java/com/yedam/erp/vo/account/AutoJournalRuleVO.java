package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class AutoJournalRuleVO {
    private Long ruleId;
    private Long companyCode;
    private String moduleType;   // 예: PAYMENT
    private String payType;      // IN / OUT
    private String payMethod;    // 현금 / 어음 / 이체 등
    private String dcType;       // D / C
    private String acctName;
    private String acctCode;
    private String remarks;
    private int priority;
    private String useYn;
}