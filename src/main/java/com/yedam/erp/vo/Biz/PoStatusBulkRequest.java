package com.yedam.erp.vo.Biz;

import java.util.List;

import lombok.Data;

@Data
public class PoStatusBulkRequest {
    private List<String> poCodes;
    private String poStatus; // "작성완료", "입금완료" 등
}