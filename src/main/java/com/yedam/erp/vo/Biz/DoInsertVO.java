package com.yedam.erp.vo.Biz;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DoInsertVO {

    private Long doNo;           // 지시서번호
    private String cusCode;      // 거래처코드
    private Long poId;           // 주문번호
    private String doCode;       // 지시서코드
    private Integer empNo;       // 사원번호
    private String name;         // 사원명
    private Date doCreated;    // 작성일자
    private Integer totalQty;    // 총주문금액
    private String doStatus;     // 진행상태
    private String notes;        // 비고
    private Long companyCode;  // 회사코드
    private Date exDate;       // 납기일자
    private String addr;         // 주소
    private String zipCode;      // 우편번호

    // 자식 테이블 매핑
    private List<DoDetailVO> dodetails;

}
