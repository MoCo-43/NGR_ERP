package com.yedam.erp.vo.Biz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PoInsertVO{
    private Long companyCode;
    private Long poId;
    private String cusCode;
    private String cusName;
    private String creater;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate poStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate exDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate poDate;

    private String prdName;
    private Long totalPrice;
    private String payMethod; // 현금/어음
    private String notes;
    private String vatType;   // 부가가치세
    private String poType;    // 거래유형

    private List<PoDetailVO> poDetails = new ArrayList<>();
}
