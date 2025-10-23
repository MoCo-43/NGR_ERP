package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerCreditVO {
  private String cusCode;
  private String cusName;
  private String creditGrade;
  private Long creditLimit;
  private Date startDate;
  private Date expireDate;
  private String activeStatus;
}