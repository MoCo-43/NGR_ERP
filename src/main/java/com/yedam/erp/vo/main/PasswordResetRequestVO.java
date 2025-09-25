package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class PasswordResetRequestVO {
    private String empId;
    private Long matNo; // loginId -> empId
}
