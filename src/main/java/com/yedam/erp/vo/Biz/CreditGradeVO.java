package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class CreditGradeVO {

  private Long companyCode;
  private String grade;
  private String gradeName;
  private Long maxLimit;
  private Long discountRate;
  private String allowCredit;
  private String prepayRequired; 
}