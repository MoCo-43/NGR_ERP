package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayDeductCodeVO {
	private String deductCode; // 공제코드
	private String deductName; // 공제명
	private String formulaTxt; // 계산식 텍스트
	private String methodDesc; // 산출방법 설명
	private String useYn; // 사용여부 Y/N
	private Long companyCode; // 회사코드
}
