package com.yedam.erp.vo.Biz;

import java.util.Date;
import lombok.Data;

@Data
public class PoHistoryVO {
    private Long companyCode;  // 회사코드
    private Date poStart;       // 주문 시작일
    private String cusName;     // 거래처명
    private String creater;     // 작성자/담당자
    private String prdName;     // 품목명
    private Date exDate;        // 납기일자
    private int unitPrice;     // 단가
}
