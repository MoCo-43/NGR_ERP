package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerCreditVO {
  private String cusCode;
  private String cusName;
  private Date dueDate;
  private Long creditLimit;
  private String creditGrade;
  private Date startDate;
  private Date expireDate;
  private Long leftPrice;
  private String activeStatus;
}
