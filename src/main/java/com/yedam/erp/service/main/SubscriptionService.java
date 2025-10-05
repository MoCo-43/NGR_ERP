package com.yedam.erp.service.main;
import java.util.Map;

import com.yedam.erp.vo.main.SubLogVO;

public interface SubscriptionService {

    /**
     * 회사정보 조회
     */
    Map<String, Object> getCompanyInfo(Long matNo);

    /**
     * 결제 준비 단계 (orderId 생성)
     */
    Map<String, Object> preparePayment(Map<String, Object> requestData, Long matNo);

    /**
     * 결제 완료 후 DB 저장 및 회사코드 반환
     */
    String completePaymentTransaction(String paymentKey, String orderId, Long amount);
    
    //구독이력
    void insertSubscriptionLog(SubLogVO logVO);

}