package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class EmpDeductDetailVO {
    private Long detailNo;         // PK 
    private Long payrollNo;        // FK 
    private String empId;          // 사번
    private String deptCode;       // 부서코드
    private String deductCode;     // 공제코드
    private String deductLabel;    // 공제명
    private Long deductAmount;     // 금액
    private Long companyCode;      // 회사코드
    private String remark;         // 비고

    private String empName;        // EMP.NAME
    private String position;       // 직급
    private String deptName;       // 부서명
}
