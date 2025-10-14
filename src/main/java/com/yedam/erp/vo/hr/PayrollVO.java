package com.yedam.erp.vo.hr;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PayrollVO {
    private Long payrollNo;        // PK
    private String yearMonth;      // YYYYMM
    private String deptCode;       // 부서코드
    private String title;          // 대장제목
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;          // 지급일자
    private String status;         // DRAFT/CONFIRMED
    private Long companyCode;      // 회사코드
    // 화면 표시용
    private String deptName;       // 부서명
    private Long empCount;         // 인원수
    
    private String jrnNo;
    private Date jrnDate;
}