package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class CreditMasterVO {
    private Long companyCode;     // 회사코드
    private Long cusCode;         // 거래처코드
    private String creditGrade;   // 신용등급
    private Long creditLimit;     // 여신한도
    private String dueDate;       // 회수예정일
    private String startDate;     // 적용시작일
    private String expireDate;    // 적용만료일
    private Long leftPrice;       // 미수잔액
    private String activeStatus;  // 활성상태 (Y/N)
    private String empName;       // 담당자명
}