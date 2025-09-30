package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayrollDeptSumVO {
	private Long payrollNo; // 급여대장번호
	private String deptCode; // 부서코드
	private String deptName; // 부서명

	private Long sumBasePay; // 기본급 합계
	private Long sumFamilyAllow; // 부양가족수당 합계
	private Long sumMealAllow; // 식대 합계
	private Long sumAnnualAllow; // 연차수당 합계

	private Long sumIncomeTax; // 소득세 합계
	private Long sumResidentTax; // 주민세 합계
	private Long sumNationalPension; // 국민연금 합계
	private Long sumHealthIns; // 건강보험 합계
	private Long sumEmployIns; // 고용보험 합계

	private Long sumDeduct; // 공제총액 합계
	private Long sumNetPay; // 실지급액 합계
	private Long companyCode; //회사코드
}
