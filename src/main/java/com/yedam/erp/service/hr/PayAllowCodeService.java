package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayAllowCodeVO;

public interface PayAllowCodeService {

    // 전체조회 
    List<PayAllowCodeVO> getAllowList(Long companyCode);

    // 단건 조회 
    PayAllowCodeVO getAllow(PayAllowCodeVO vo);

    // 등록 
    int addAllow(PayAllowCodeVO vo);

    // 수정 
    int editAllow(PayAllowCodeVO vo);
    //사용여부
    int changeUseYn(PayAllowCodeVO vo);
}
