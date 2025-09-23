package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class DeptVO {
    private String dept_code;      // 부서코드 
    private String dept_name;      // 부서명
    private String use_yn;         // 사용여부
    private int member_cnt;        // 부서 인원수
    private String manager_emp_id; // 부서장 사번
    private String manager_name;
}
