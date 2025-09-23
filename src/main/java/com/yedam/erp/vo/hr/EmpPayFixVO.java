package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class EmpPayFixVO {
    private String emp_id;       // PK
    private Long   base_pay;     // 기본급
    private Long   birth_care;   // 출산/보육 수당
    private Long   family_allow; // 부양가족수당
    private Long   meal_allow;   // 식대
    private Long   annual_allow; // 연차수당
}