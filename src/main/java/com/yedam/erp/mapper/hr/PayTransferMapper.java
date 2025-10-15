package com.yedam.erp.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.PayTransferVO;

@Mapper
public interface PayTransferMapper {
   
    // 확정건 조회
    List<PayTransferVO> selectTransferViewFromConfirmed(Map<String, Object> param);


}
