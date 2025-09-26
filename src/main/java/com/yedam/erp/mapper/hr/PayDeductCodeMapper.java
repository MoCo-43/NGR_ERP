package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.PayDeductCodeVO;

@Mapper
public interface PayDeductCodeMapper {

    // 전체조회 
    List<PayDeductCodeVO> selectDeductList(Long companyCode);

    // 단건 조회
    PayDeductCodeVO selectDeduct(PayDeductCodeVO vo);

    // 등록
    int insertDeduct(PayDeductCodeVO vo);

    // 수정
    int updateDeduct(PayDeductCodeVO vo);

    // 사용여부 변경
    int updateUseYn(PayDeductCodeVO vo);
}
