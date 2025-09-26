package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.PayAllowCodeVO;

@Mapper
public interface PayAllowCodeMapper {

    // 전체조회 
    List<PayAllowCodeVO> selectAllowList(Long companyCode);

    // 단건 조회 
    PayAllowCodeVO selectAllow(PayAllowCodeVO vo);

    // 등록 
    int insertAllow(PayAllowCodeVO vo);

    // 수정 
    int updateAllow(PayAllowCodeVO vo);
    
    //시용여부
	int updateUseYn(PayAllowCodeVO vo);
}
