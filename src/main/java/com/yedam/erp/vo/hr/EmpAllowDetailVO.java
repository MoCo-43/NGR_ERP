
package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class EmpAllowDetailVO {
    private Long detailNo;         // PK (SEQ_EMP_ALLOW_DETAIL)
    private Long payrollNo;        // FK -> PAYROLL.PAYROLL_NO
    private String empId;          // 사번
    private String deptCode;       // 부서코드
    private String allowCode;      // 수당코드(PAY_ALLOW_CODE)
    private String allowLabel;     // 수당명(표시용, PIVOT 헤더)
    private Long allowAmount;      // 금액
    private Long companyCode;      // 회사코드(Long)
    private String remark;         // 비고(옵션)

    private String empName;        // EMP.NAME
    private String position;       // 직급
    private String deptName;       // 부서명
}
