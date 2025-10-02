package com.yedam.erp.vo.hr;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class PayrollVO {
    private Long payrollNo;        // 급여대장번호 (PK)
    private String yearMonth;      // 귀속연월 'YYYYMM'
    private String deptCode;       // 부서코드
    private String title;          // 대장 제목
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;          // 지급일자
    private String status;         // 상태 (DRAFT/CONFIRMED)
    private Long companyCode;      // 회사코드 
    private String deptName;       // 부서명
    private Long empCount;         // 인원수
    private Date jrnDate;
    private String jrnNo;
}
