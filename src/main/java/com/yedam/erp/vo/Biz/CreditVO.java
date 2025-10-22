package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class CreditVO {

  private String cusCode;
  private Long creditLimit;
  private String creditGrade;
  private Date startDate;
  private Date expireDate;
  private Long leftPrice;
  private String cActiveStatus;
  private Long companyCode;
}
