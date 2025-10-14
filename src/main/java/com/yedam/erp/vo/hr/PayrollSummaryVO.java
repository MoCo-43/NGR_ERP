package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayrollSummaryVO {
    private Long sumNo;           // 합계 PK
    private Long payrollNo;       // 급여대장번호
    private String empId;         // 사원ID
    private String empName;       // 사원명
    private Long totalAllow;      // 지급총액
    private Long totalDeduct;     // 공제총액
    private Long netPay;          // 실지급액
    private String remarks;       // 비고
    private Long companyCode;     // 회사코드
}
