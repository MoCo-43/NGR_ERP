package com.yedam.erp.vo.account;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PaymentHeaderVO {
    private Long payCode;        // PK (시퀀스)
    private String payNo;        // 전표번호 FN_NEXT_VOUCHER_NO('M', SYSDATE)
    private Date payDate;        // 결제일자
    private String payType;      // IN/OUT
    private String cusCode;      // 거래처코드
    private String cusName;      // 거래처명
    private String method;       // 결제방식
    private String cashAccount;  // 계좌번호
    private Long amount;         // 총금액
    private String memo;         // 비고
    private Date createdAt;      // 생성일시
    private Long companyCode;  // 회사코드
    private String postedFlag;   // 일반전표 반영여부 (N/Y)
    private Date updatedAt;
    
    
    // ✅ 상세 리스트 포함
    private List<PaymentApplyVO> applies;
}