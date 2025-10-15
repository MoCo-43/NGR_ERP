package com.yedam.erp.vo.hr;

import lombok.Data;

@Data
public class PayTransferVO {

    private Long transferNo;        //이체번호
    private String payYm;          // 급여 연월 
    private String empId;          // 사원번호
    private String bankCode;       // 은행코드
    private String accountNo;      // 계좌번호
    private String accountHolder;  // 예금주
    private Long netPay;           // 실지급액
    private Long companyCode;    // 회사코드

}
