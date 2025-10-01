package com.yedam.erp.service.account;

import java.util.List;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

public interface InvoiceService {
	 // 헤더 목록
    List<InvoiceHeaderVO> getInvoiceHeaders(Long companyCode);

    // 특정 전표의 라인들
    List<InvoiceLineVO> getInvoiceLines(Long invoiceCode, Long companyCode);

    // 헤더 라인
    void saveInvoiceWithLines(InvoiceHeaderVO header);
    
    InvoiceHeaderVO getInvoiceHeader(Long companyCode, Long invoiceCode);
    
    InvoiceHeaderVO getInvoiceWithLines(Long companyCode, Long invoiceCode);
    
    void updatePostedFlag(Long companyCode, Long invoiceCode, String postedFlag);
}