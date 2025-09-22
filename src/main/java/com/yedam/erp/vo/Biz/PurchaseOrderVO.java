package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class PurchaseOrderVO {
   private Long poId;         // 주문번호 (PK)
   private Long cusNo;        // 거래처 번호 (FK)
   private String poCode;     // 주문 코드
   private String creater;    // 작성자
   private Date poStart;      // 주문 시작일
   private Long totalPrice;   // 총 금액
   private String poStatus;   // 주문 상태
   private String payMethod;  // 결제 방법
   private String notes;      // 비고
   private Date exDate;       // 예상 납기일
   private String cusName;    // 거래처명
   private String prdName;    // 제품명
}
