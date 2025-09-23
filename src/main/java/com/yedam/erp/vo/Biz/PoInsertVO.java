package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class PoInsertVO {

    // PURCHASE_ORDER 테이블 필드
    private String cusNo;       // 거래처 번호
    private String creater;     // 담당자(작성자)
    private Date poStart;       // 작성일자
    private Date exDate;        // 납기일자
    private Date poDate;        // 주문일자
    private String prdName;     // 대표 품목명
    private int totalPrice;     // 총액
    private String poStatus;    // 처리상태
    private String payMethod;   // 지불방식
    private String notes;       // 비고
    private String vatType;     // 부가세 유형
    private String poType;      // 거래 유형

    // PO_DETAIL 테이블 필드
    private String productCode; // 품목코드
    private int orderQty;       // 주문수량
    private int unitPrice;      // 단가
    private int dcPrice;        // 할인금액
    private int supAmt;         // 공급가액
    private int vatAmt;         // 부가가치세
    private String dPrdName;    // 상세 품목명
}
