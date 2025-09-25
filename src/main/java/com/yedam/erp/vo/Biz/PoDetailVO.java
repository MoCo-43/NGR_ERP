package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class PoDetailVO {
  private int podId;
  private int poId;
  private String productCode;
  private int orderQty;
  private int unitPrice;
  private int dcPrice;
  private int supAmt;
  private int vatAmt;
  private String dPrdName;
}
