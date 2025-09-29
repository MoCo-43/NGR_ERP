package com.yedam.erp.vo.Biz;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class JoinPoVO {
    private Date poStart;        // 주문 시작일자
    private String cusName;      // 거래처명
    private String creater;      // 작성자
    private Long companyCode;   // 회사코드
    private String productName;   // 제품명
    private String bizType;      // 업태
    private BigDecimal totalPrice; // 총 금액
    private String poStatus;     // 주문 상태
    private String notes;        // 비고
}
