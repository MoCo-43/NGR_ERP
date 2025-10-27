package com.yedam.erp.vo.Biz;
import lombok.Data;

@Data
public class CusModalVO {

  private Long companyCode;  // 회사코드
  private String cusCode;  // 거래처코드
  private String cusName;  // 거래처이름
  private String creditGrade;  // 여신등급
  private Long leftPrice;  // 여신잔여금액
  private Long discountRate;  // 여신할인율

  // 긴급출하용 거래처정보들고오기
  private String cempName; 
  private String tel;
  private String addr;
  private String zipCode;
  
  // 거래처 정보
  private String bizNo;
  private String ceoName;
  private String empName;
  private String bizType;
  private String bizCategory;

}