package com.yedam.erp.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.PayLogVO;

@Mapper
public interface PayLogMapper {
    // 특정 구독 코드의 결제 내역 조회
    List<PayLogVO> payLogList(String subCode);
}
