package com.yedam.erp.vo.hr;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmpEduVO {
	  private Long eduNo;          // 교육번호 [PK]
	    private String empId;        // 사번 (EMP-YYYY-###)
	    private String eduName;      // 교육명
	    private String eduType;      // 교육종류
	    private String result;       // 결과 (수료/미수료)
	    private Integer score;       // 점수
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date completeAt;// 수료일
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date expiresAt; // 유효기간
	    private String remarks;      // 비고

}
