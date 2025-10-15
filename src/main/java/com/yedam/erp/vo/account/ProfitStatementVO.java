package com.yedam.erp.vo.account;

import lombok.Data;

@Data
public class ProfitStatementVO {
    private String acctCode;
    private String acctName;
    private String acctGroup;
    private Double prevAmt;
    private Double currAmt;
}