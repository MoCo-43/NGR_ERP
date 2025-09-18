package com.yedam.erp.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class VoucherVO {
    private Long id;
    private String voucherNo;
    private String type;            // 'S' or 'P'
    private LocalDate voucherDate;
    private String partnerName;
    private BigDecimal amount;
    private String status;          // OPEN / CLOSED
}
