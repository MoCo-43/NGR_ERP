package com.yedam.erp.service.account;

import java.util.List;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

public interface InvoiceService {
	  List<InvoiceHeaderVO> getInvoiceHeaders(Long companyCode);

	    List<InvoiceLineVO> getInvoiceLines(String invoiceCode);

	    void saveInvoice(InvoiceHeaderVO header);
}