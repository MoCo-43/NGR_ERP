package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayAllowCodeVO;

public interface PayAllowCodeService {

    // 리스트 
    List<PayAllowCodeVO> getList(PayAllowCodeVO param);

    // 단건
    PayAllowCodeVO get(String allowCode);

    // 등록
    boolean insert(PayAllowCodeVO vo);

    // 수정
    boolean update(PayAllowCodeVO vo);

    // 사용여부 변경 
    boolean changeUseYn(String allowCode, String useYn);
}
