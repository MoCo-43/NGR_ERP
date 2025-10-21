package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class CreditGradeVO {

  private String companyCode;
  private String grade;
  private String gradeName;
  private Long maxLimit;
  private Long discountRate;
  private Long monDiscCnt;
  private String allowCredit;
  private String prepayRequired; 
}