package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class PasswordResetRequestVO {
    private String empId;
    private String comCode; // loginId -> empId
}
