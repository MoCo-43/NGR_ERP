package com.yedam.erp.vo.main;

import java.util.Date;
import lombok.Data;

@Data
public class PwResetTokenVO {
    private Long tokenId;       // PK
    private Long empIdNo;       // 누구의 토큰인지 (emp_login 테이블 FK)
    private String token;       // UUID로 생성된 고유 토큰 값
    private Date startDate;     // 토큰 생성 시간
    private Date endDate;       // 토큰 만료 시간
}