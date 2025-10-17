package com.yedam.erp.service.main;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.main.SubLogVO;
import com.yedam.erp.vo.main.SubscriptionVO;

public interface SubscriptionService {
	
	//회사정보 조회
    Map<String, Object> getCompanyInfo(Long matNo);

    // 결제 준비 단계 (orderId 생성)
    Map<String, Object> preparePayment(Map<String, Object> requestData, Long matNo);

    //결제 완료 후 DB 저장 및 회사코드 반환
    String completePaymentTransaction(String paymentKey, String orderId, Long amount);
    
    //구독이력
    void insertSubscriptionLog(SubLogVO logVO);
    //구독 내역
    List<SubscriptionVO> findSubscriptionsByComCode(String comCode);
    //SubscriptionVO findLatestSubscriptionByComCode(@Param("comCode") String comCode);
    SubscriptionVO findLatestSubscriptionByMatNo(@Param("matNo") Long matNo);
 // 빌링키를 DB에 저장하는 서비스 메서드
    //void saveBillingKey(String customerKey, String billingKey);
    String processFirstRecurringPayment(String customerKey, String billingKey); 
    SubscriptionVO createPendingSubscription(SubscriptionVO subscriptionVO);
    //자동결제 대상조회
    List<SubscriptionVO> findSubscriptionsDueForPaymentToday();
    //자동결제 1건처리
    void processAutomaticPayment(SubscriptionVO subscription);

}