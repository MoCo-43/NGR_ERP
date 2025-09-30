package com.yedam.erp.service.account;

import java.util.List;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

public interface InvoiceService {
    List<InvoiceHeaderVO> getInvoiceHeaders(Long companyCode);
    List<InvoiceLineVO> getInvoiceLines(String invoiceCode);

    int createInvoiceHeader(InvoiceHeaderVO vo);
    int createInvoiceLine(InvoiceLineVO vo);
    
    // ✅ 헤더 + 라인 일괄 저장
    void createInvoiceWithLines(InvoiceHeaderVO header, List<InvoiceLineVO> lines);	
}