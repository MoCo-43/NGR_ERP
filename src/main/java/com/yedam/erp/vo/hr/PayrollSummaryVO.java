package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayrollSummaryVO {
    private Long summaryNo;      // 합계번호 (PK)
    private Long payrollNo;      // 급여대장번호 (FK)
    private String empId;        // 사번
    private String empName;      // 성명
    private String position;     // 직급

    private int basePay;         // 기본급
    private int familyAllow;     // 부양가족수당
    private int mealAllow;       // 식대
    private int annualAllow;     // 연차수당
    private int totalPay;        // 지급총액

    private int incomeTax;       // 소득세
    private int residentTax;     // 주민세
    private int nationalPension; // 국민연금
    private int healthIns;       // 건강보험
    private int employIns;       // 고용보험
    private int totalDeduct;     // 공제총액

    private int netPay;          // 실지급액
}
