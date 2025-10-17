package com.yedam.erp.vo.main;

import java.util.Date;

import lombok.Data;

@Data
public class CompanyVO {
	private Long matNo;//회사등록 순번
	private String ceo;
	private String compName;
	private String matName;
	private String matTel;
	private String matMail;
	private String compAddr;
	private Date subDate;
	private String zipCode;
	private String comTel;
	private String compDelAddr;
	private String comCode; //회사코드
	private String jibunAddress;

	private Long brm; // 사업자번호
	private String bizType; // 업태
	private String bizCategory; // 종목 ex)냉동식품
	private String bizAccount; //계좌번호
}
