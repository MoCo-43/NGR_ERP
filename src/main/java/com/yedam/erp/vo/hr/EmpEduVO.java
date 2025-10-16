package com.yedam.erp.vo.hr;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EmpEduVO {
    private Long   eduNo;     // PK
    private String empId;     // 사번
    private String eduName;   // 교육명
    private String eduType;   // 종류
    private String result;    // 결과
    private Integer score;    // 점수

    @DateTimeFormat(pattern = "yyyy-MM-dd")     // 요청 파싱
    @JsonFormat(pattern = "yyyy-MM-dd")         // 응답 직렬화
    private Date completeAt; // 수료일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiresAt;  // 유효기간

    private String remarks;  // 비고
}
