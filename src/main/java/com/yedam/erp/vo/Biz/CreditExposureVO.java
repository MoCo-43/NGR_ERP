package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class CreditExposureVO {
    private Long companyCode;    // 회사코드
    private Long cusCode;        // 거래처코드
    private String cusName;      // 거래처명
    private String creditGrade;  // 신용등급 (A/B/C...)
    private Long creditLimit;    // 여신한도
    private String startDate;    // 적용시작일
    private String expireDate;   // 적용만료일
    private Long remainLimit;    // 잔여한도
    private String managerName;  // 담당자명
    private String status;       // 상태 (NORMAL, BLOCK, etc.)
}