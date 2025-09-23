package com.yedam.erp.vo.hr;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EmpCertVO {
    private Long certNo;        // PK
    private String empId;       // 사번
    private String certName;    // 자격증명
    private String grade;       // 등급
    private String orgName;     // 발급기관
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")         // 응답 직렬화(JSON)
    private Date acquiredAt;    // 취득일
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")         // 응답 직렬화(JSON)
    private Date expiresAt;     // 유효기간
    private String remarks;     // 비고
}
