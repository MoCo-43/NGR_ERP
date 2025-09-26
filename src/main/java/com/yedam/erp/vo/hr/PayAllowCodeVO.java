package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayAllowCodeVO {
    private String allowCode;   // 수당코드
    private String allowName;   // 수당명
    private String taxType;     // 과세/비과세 유형
    private String payType;     // 지급유형
    private String methodDesc;  // 산출방법 설명
    private String useYn;       // 사용여부
    private Long   companyCode; // 회사코드
}
