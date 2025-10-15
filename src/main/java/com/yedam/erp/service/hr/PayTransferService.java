package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayTransferVO;

public interface PayTransferService {  
    // 확정건 조회
    List<PayTransferVO> getTransferViewFromConfirmed(Long companyCode, String payYm);

}
