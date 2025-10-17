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
  private Long monDiscCnt;  // 남은 월별 할인 횟수
}