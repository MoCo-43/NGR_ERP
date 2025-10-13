package com.yedam.erp.service.account;

import java.util.List;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.PaymentHeaderVO;

public interface MoneyService {

    // 미결 전표 조회
    List<InvoiceHeaderVO> getUnpaidInvoices(String type, String fromDate, String toDate, String searchCus, Long companyCode);
    
    // 자금전표 + 상세 + 미결금액 트랜잭션 등록
    void savePayment(PaymentHeaderVO payment);
    
    // 조회
    List<PaymentHeaderVO> getPaymentList(Long companyCode);
    
 // ✅ 일반전표 등록 후 상태 업데이트
    void updatePaymentPosted(Long payCode, Long companyCode, String postedFlag);
}