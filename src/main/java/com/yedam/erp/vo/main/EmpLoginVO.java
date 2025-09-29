package com.yedam.erp.vo.main;

import java.util.Date;

import lombok.Data;

@Data
public class EmpLoginVO {
	private Long empIdNo;
	private String empPw;
	private String empId;
	private Long empNo;
	private Date createAt;
	private String isUsed;
	private Long  matNo;
	private String isLocked;
	private Date lockUntil;
	private Long failedLoginAtt;
	private String deptCode;
	private String codeId;
	private String twoStepMethod;
	private String token;
	private String empMobile;	
}
