package com.yedam.erp.mapper.main;

import java.util.Map;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
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
}