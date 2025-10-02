package com.yedam.erp.vo.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 만듭니다.
public class PaymentPrepareResponseVO {
    private String orderId;     // 서버가 생성한 고유 주문번호
    private String orderName;   // 주문명 (예: 프리미엄 플랜 구독)
    private long amount;        // 서버가 최종 계산하고 검증한 결제 금액
}