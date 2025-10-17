package com.yedam.erp.vo.Biz;

import lombok.Data;
@Data
public class ProductCodeVO {
  private String productCode;
  private String productName;
  private Long salesPrice;
  private String vatType;
  private Long companyCode;

}