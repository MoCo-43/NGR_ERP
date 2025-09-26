package com.yedam.erp.vo.Biz;

import lombok.Data;

@Data
public class CustomerVO {
    private Long companyCode;
    private Long cusNo;
    private String cusCode;
    private Long empNo;
    private String cusName;
    private String bizNo;
    private String ceoName;
    private String bizType;
    private String bizCategory;
    private String tel;
    private String email;
    private String addr;
    private Integer zipCode;
    private String cusType;
    private String activeStatus;
}
