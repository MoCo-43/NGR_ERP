package com.yedam.erp.vo.Biz;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PoHistoryVO {
    private Long companyCode;
    private String poCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate poStart;
    private String cusCode;
    private String cusName;
    private String creater;
    private String productCode;
    private String productName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate exDate;
    private Integer orderQty;
    private Long unitPrice; 
    private Long supAmt;
    private String zipCode;
    private String addr;
    private String tel;
}
