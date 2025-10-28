package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerCreditVO {
  private String cusCode;  // 거래처코드
  private String cusName;  // 거래처이름
  private String creditGrade;  // 여신등급
  private Long creditLimit;  // 여신한도
  private Date startDate;  // 여신적용일
  private Date expireDate;  // 여신만료일
  private String activeStatus;  // 여신활성화여부
  private Long leftPrice;  // 여신잔여금액
  private String companyCode;  // 회사코드
}