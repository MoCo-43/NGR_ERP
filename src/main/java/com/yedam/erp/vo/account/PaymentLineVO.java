package com.yedam.erp.vo.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLineVO {

    private String yearMonth;
    private String deptCode;
    private String dcType;     // D or C
    private String acctCode;
    private String acctName;
    private String itemKey;    // ex) BASE_PAY_SUM
    private Long amount;
    private Long companyCode;
    private String itemLabel;
}