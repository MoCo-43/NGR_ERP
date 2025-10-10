package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class PaymentConfirmRequestVO {
    private String paymentKey;  // 토스페이먼츠가 발급한 결제 건별 고유 키
    private String orderId;     // 결제 준비 단계에서 서버가 생성했던 주문번호
    private Long amount;        // 최종 승인된 결제 금액
}
