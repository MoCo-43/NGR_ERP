package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class PoDetailVO{
  private Long companyCode;  // 회사코드
  private int podId;  // 주문서상세번호
  private int poId;  // 주문번호
  private String productName;  // 품목이름
  private String productCode;  // 품목코드
  private int orderQty;  // 주문수량
  private int unitPrice;  // 단가
  private int dcAmt;  // 할인금액
  private int supAmt;  // 공급가액
  private int vatAmt;  // 부가세액
  private String vatType;   // 부가세여부
}
