package com.yedam.erp.mapper.hr;

import java.util.List;
import com.yedam.erp.vo.hr.PayAllowCodeVO;

public interface PayAllowCodeMapper {

    // 목록 조회
    List<PayAllowCodeVO> selectPayAllowList(PayAllowCodeVO param);

    // 단건 조회
    PayAllowCodeVO selectPayAllow(String allowCode);

    // 등록
    int insertPayAllow(PayAllowCodeVO vo);

    // 수정
    int updatePayAllow(PayAllowCodeVO vo);

    // 사용여부 변경
    int updateUseYn(PayAllowCodeVO vo);
}
