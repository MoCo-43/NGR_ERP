package com.yedam.erp.vo.account;

import java.util.Date;
import java.util.List;

import com.yedam.erp.vo.Biz.CustomerVO;

import lombok.Data;

@Data
public class InvoiceHeaderVO {
    private Long invoiceCode;   // 전표코드
    private String invoiceNo;     // 전표번호
    private String type;          // 매출/매입 구분
    private Date invoiceDate;     // 전표일자
    private String orderCode;     // 주문서 코드
    private String deptName;      // 부서코드
    private String cusCode;       // 거래처 코드
    private String bizNo;         // 사업자번호
    private String cusName;       // 거래처명
    private String vatType;       // 부가세 유형
    private Date dueDate;         // 결제예정일

    private Long subtotalAmt;     // 공급가액 합계
    private Long vatAmt;          // 부가세 합계
    private Long totalAmt;        // 총금액

    private String payMethod;     // 결제방식
    private String memo;          // 메모/적요
    private String cashAccount;   // 현금계정
    private String status;        // 상태 (OPEN, CLOSED, POSTED 등)

    private Date createdAt;       // 생성일
    private Date updatedAt;       // 수정일
    private Long companyCode;   // 회사코드
    private String postedFlag;    // 전표반영 여부
    private Long unpaidAmt; // 미결금액
    private String bankName;
    // 1:N 매핑
    private List<InvoiceLineVO> lines;
    
    // 거래처
    private String buyerCeoName;
    private String buyerAddr;
    private String buyerBizType;
    private String buyerBizCategory;
    private String buyerEmail;


    
}