package com.yedam.erp.vo.main;

import lombok.Data;

@Data
public class TossConfirmResponseVO {
    private String status;      // 결제 처리 상태 (DONE, CANCELED 등)
    private String method;      // 결제 수단 (카드, 가상계좌 등)
    private String paymentKey;
    private String orderId;
    private Long totalAmount;
    // ... 필요한 다른 필드들을 추가할 수 있습니다. (예: 카드 정보, 가상계좌 정보 등)
}
