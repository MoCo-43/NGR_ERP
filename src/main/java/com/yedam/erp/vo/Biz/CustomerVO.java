package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class CustomerVO {
    private Long companyCode;  // 회사 코드
    private Long cusNo;  // 거래처 번호
    private String cusCode;  // 거래처 코드
    private Long empNo;  // 담당자 번호
    private String empName;  // 담당자 이름
    private String cusName;  // 거래처명
    private String bizNo;  // 사업자 번호
    private String ceoName;  // 대표자명
    private String bizType;  // 업종
    private String bizCategory;  // 사업자 구분
    private String tel;  // 전화번호
    private String email;  // 이메일
    private String addr;  // 주소
    private Integer zipCode;  // 우편번호
    private String cusType;  // 거래처 유형
    private String activeStatus;  // 활성 상태
    private String gbn;  // 구분
    private String bankName ;  // 은행명
    private String bankAccount; // 은행 계좌
    private String cempName;  // 거래 담당자명
}
