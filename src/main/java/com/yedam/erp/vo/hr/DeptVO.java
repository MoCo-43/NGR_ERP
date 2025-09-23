package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class DeptVO {
    private String deptCode;     // 부서코드 
    private String deptName;     // 부서명
    private String useYn;        // 사용여부
    private int memberCnt;       // 부서 인원수
    private String managerEmpId; // 부서장 사번
}
