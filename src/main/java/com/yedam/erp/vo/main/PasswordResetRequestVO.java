package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class PasswordResetRequestVO {
	//1단계 휴대폰 인증 사용
    private String empId;
    private String comCode; // loginId -> empId
    private String matMail;
    //2단계 1단계 응답 받은 값 담는 필드,사용자 입력인증번호 담는 필드
    private String userKey;
    private String smsCode;
    
}
