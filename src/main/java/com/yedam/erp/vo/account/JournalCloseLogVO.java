package com.yedam.erp.vo.account;

import java.util.Date;

import lombok.Data;

@Data
public class JournalCloseLogVO {
    private Long logId;
    private Long companyCode;
    private String jrnRange;    // 전표번호 범위 (ex: 001~005)
    private String actionType;  // approve / reverse
    private Long debitSum;
    private Long creditSum;
    private Date actionDate;
    private String status;      // active/inactive
    private String createdBy;
    private String remarks;
}