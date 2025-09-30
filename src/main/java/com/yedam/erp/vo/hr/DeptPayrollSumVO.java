package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class DeptPayrollSumVO {
    private Long confirmNo;       // PK
    private String yearMonth;     // 귀속연월
    private String deptCode;      // 부서코드
    private Integer empCount;     // 인원수
    private Long totalAllowSum;   // 지급총액 합계
    private Long totalDeductSum;  // 공제총액 합계
    private Long netPaySum;       // 실지급액 합계

    private Long basePaySum;         // 기본급 합계
    private Long familyAllowSum;     // 부양가족수당 합계
    private Long mealAllowSum;       // 식대 합계
    private Long annualAllowSum;     // 연차수당 합계
    private Long incomeTaxSum;       // 소득세 합계
    private Long residentTaxSum;     // 주민세 합계
    private Long nationalPensionSum; // 국민연금 합계
    private Long healthInsSum;       // 건강보험 합계
    private Long employInsSum;       // 고용보험 합계

    private Long companyCode;     // 회사코드
}
