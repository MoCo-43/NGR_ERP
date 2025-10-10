package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class EmpPayFixVO {
    // PK
    private Long payFixNo;

    // 사번
    private String empId;

    // 수당 코드
    private String empAllowCode;

    // 수당명
    private String empAllowLabel;

    // 금액
    private Long empAllowPay;

    // 회사코드
    private Long companyCode;
}
