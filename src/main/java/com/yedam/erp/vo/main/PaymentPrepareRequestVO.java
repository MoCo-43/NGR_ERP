package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class PaymentPrepareRequestVO {
    private Long subPlanNo;
    private int userCount;
    private int duration;
    private long finalAmount;
    private long adjustmentCharge;
}
