package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayrollSummaryVO {
	private Long summaryNo; // 합계번호 (PK)
	private Long payrollNo; // 급여대장번호 (FK)
	private String empId; // 사번
	private String empName; // 성명
	private String position; // 직급

	private Long basePay; // 기본급
	private Long familyAllow; // 부양가족수당
	private Long mealAllow; // 식대
	private Long annualAllow; // 연차수당
	private Long totalAllow; // 지급총액

	private Long incomeTax; // 소득세
	private Long residentTax; // 주민세
	private Long nationalPension; // 국민연금
	private Long healthIns; // 건강보험
	private Long employIns; // 고용보험
	private Long totalDeduct; // 공제총액

	private Long netPay; // 실지급액
}
