package com.yedam.erp.vo.Biz;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DeliveryOrderVO {
    private Long companyCode;   // 회사 코드
    private Long doNo;          // 출하지시 번호
    private String doCode;      // 출하지시 코드
    private Date doCreated;     // 출하지시 등록일
    private String cusCode;     // 거래처 코드
    private String cusName;     // 거래처 명
    private Date exDate;        // 출고예정일
    private String doStatus;    // 출하지시 상태
    private Integer totalQty;   // 총 수량
    private String notes;       // 비고
    private String creater;     // 생성자
    private String productCode ; // 제품 코드 
    private String productName ; // 제품 명

    List<DoDetailVO> doDetails; // 출하지시 상세 목록
}
