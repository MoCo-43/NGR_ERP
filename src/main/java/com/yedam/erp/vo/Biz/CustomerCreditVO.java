package com.yedam.erp.vo.Biz;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class CustomerCreditVO {
    private Long companyCode;   // 회사
    private String cusCode;       // 거래처코드
    private String cusName;       // (있으면 조인해서)
    private String creditGrade;   // A/B/C/D/E
    private Long creditLimit;   // 여신한도
    private LocalDate startDate;      // 적용시작일
    private LocalDate expireDate;     // 적용만료일
    private Long usedLimit;     // 미수합계 (=사용액)
    private Long remainLimit;   // 잔여한도 = 한도-미수
    private Long discLimitCnt;     // 월허용건수 (A=3,B=1,else 0)
    private Long discUsedCnt;      // 이번달 할인 사용건수
    private Long discLeftCnt;      // 할인잔여횟수 = max(허용-사용, 0)
}
