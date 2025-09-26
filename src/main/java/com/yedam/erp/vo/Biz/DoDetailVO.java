package com.yedam.erp.vo.Biz;

import java.util.Date;

import lombok.Data;

@Data
public class DoDetailVO {
    private Long companyCode;
    private Long doNo;          // 출하지시 번호
    private String cusCode;     // 고객 코드
    private Long poNo;          // 주문서 번호
    private String doCode;      // 출하지시 코드
    private Long empNo;         // 담당자 번호
    private String name;        // 담당자명
    private Date doCreated;      // 작성일자 (DO_CRATED -> 오타 같음: CREATED?)
    private Integer totalQty;   // 총 수량
    private String doStatus;    // 상태 (작성완료/처리중/출하완료)
    private String notes;       // 비고
    private Date exDate;        // 출하예정일 (납기일)
}

