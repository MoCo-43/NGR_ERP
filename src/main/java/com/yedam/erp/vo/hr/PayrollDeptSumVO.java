package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayrollDeptSumVO {
    private Long payrollNo;         // 급여대장번호
    private String deptCode;        // 부서코드
    private String deptName;        // 부서명

    private int sumBasePay;         // 기본급 합계
    private int sumFamilyAllow;     // 부양가족수당 합계
    private int sumMealAllow;       // 식대 합계
    private int sumAnnualAllow;     // 연차수당 합계

    private int sumIncomeTax;       // 소득세 합계
    private int sumResidentTax;     // 주민세 합계
    private int sumNationalPension; // 국민연금 합계
    private int sumHealthIns;       // 건강보험 합계
    private int sumEmployIns;       // 고용보험 합계

    private int sumDeduct;          // 공제총액 합계
    private int sumNetPay;          // 실지급액 합계
}
