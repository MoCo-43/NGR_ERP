package com.yedam.erp.mapper.main;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    //첫 결제 실행시 pending 구독 정보를 조회
    SubscriptionVO findPendingSubscriptionByCustomerKey(@Param("customerKey") String customerKey); // ★ 신규 추가
    // 빌링키만 업데이트 하는 메서드인데 첫 결제 로직 사용 x
    int updateBillingKeyByCustomerKey(@Param("customerKey") String customerKey, @Param("billingKey") String billingKey);
    //정기결제 pending 상태 구독정보 저장
    int insertBiling(SubscriptionVO subscriptionVO);
    int activatePendingSubscription(SubscriptionVO subscriptionVO);
    //next_pay_date 도래한 active 구독 조회
    List<SubscriptionVO> findSubscriptionsDueForPaymentToday();
    //자동결제 성공 DB 프로시저 호출
    void processPayment(PayLogVO payLogVO);
    // 구독 취소
    //int cancelSubscription(Long subCode);
    void callCancelSubscriptionProc(Map<String, Object> params);
    // 구독 정보 조회 (남은 기간 확인용)
    //SubscriptionVO getSubscriptionBySubCode(Long subCode);
    SubscriptionVO getSubscriptionBySubCode(@Param("subCode") String subCode);
}