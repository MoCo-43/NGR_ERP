package com.yedam.erp.vo.Biz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PoInsertVO{
    private Long companyCode;  // 회사코드
    private Long poId;  // 주문번호
    private String poCode;  // 주문코드
    private String cusCode;  // 거래처코드
    private String creater;  // 작성일자

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate poStart;  // 작성일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate exDate;  // 납기일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate poDate;  // 결제예정일
    private String payMethod; // 현금/어음
    private String notes;  // 비고
    private String creditActive; //할인적용여부

    private List<PoDetailVO> poDetails = new ArrayList<>();  // 주문서 상세
}
