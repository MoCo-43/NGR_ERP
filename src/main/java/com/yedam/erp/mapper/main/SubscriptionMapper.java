package com.yedam.erp.mapper.main;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.main.PayLogVO;
import com.yedam.erp.vo.main.SubscriptionVO;

@Mapper
public interface SubscriptionMapper {

    // 회사코드 최댓값 조회
    String getMaxComCode();

    // 구독 정보 등록
    void insertSubscription(SubscriptionVO vo);

    // 회사코드 및 기본정보 업데이트
    void updateCompanyComCode(Map<String, Object> params);

    // 사업자번호로 회사 정보 조회
    Map<String, Object> selectCompanyInfoByMatNo(Long matNo);
    //구독 내역
    List<SubscriptionVO> findSubscriptionsByComCode(String comCode);
    //구독최신내역 
    SubscriptionVO findLatestSubscriptionByMatNo(@Param("matNo") Long matNo);
 // 빌링키 업데이트를 위한 메서드
    int updateBillingKeyByCustomerKey(@Param("customerKey") String customerKey, @Param("billingKey") String billingKey);
    int insertBiling(SubscriptionVO subscriptionVO);
    List<SubscriptionVO> findSubscriptionsDueForPaymentToday();
    void processPayment(PayLogVO payLogVO);
}