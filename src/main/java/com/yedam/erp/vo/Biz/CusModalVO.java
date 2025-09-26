package com.yedam.erp.vo.Biz;
import lombok.Data;

@Data
public class CusModalVO {

  private Long companyCode;
  /** 고객 코드 */
  private String cusCode;
  /** 고객명 */
  private String cusName;
  /** 신용 등급 */
  private String creditGrade;
  /** 잔여 금액 */
  private int leftPrice;
}
