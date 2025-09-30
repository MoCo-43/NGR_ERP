package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.InvoiceMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.InvoiceService;
import com.yedam.erp.vo.account.InvoiceHeaderVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

	  private final InvoiceMapper invoiceMapper;

	    @Override
	    public List<InvoiceHeaderVO> getInvoiceHeaders(Long companyCode) {
	        return invoiceMapper.selectInvoiceHeaders(companyCode);
	    }

	    @Override
	    public List<com.yedam.erp.vo.account.InvoiceLineVO> getInvoiceLines(String invoiceCode) {
	        return invoiceMapper.selectInvoiceLinesByHeader(invoiceCode);
	    }

	    @Override
	    @Transactional
	    public void saveInvoice(InvoiceHeaderVO header) {
	        // 회사코드 강제 세팅
	        header.setCompanyCode(SessionUtil.companyId());

	        // 1. 전표번호 생성
	        String nextNo = invoiceMapper.selectNextInvoiceNo();
	        header.setInvoiceNo(nextNo);

	        // 2. 헤더 저장 (invoiceCode는 selectKey로 채워짐)
	        invoiceMapper.insertInvoiceHeader(header);

	        // 3. 라인 저장
	        if (header.getLines() != null && !header.getLines().isEmpty()) {
	            header.getLines().forEach(line -> {
	                line.setCompanyCode(header.getCompanyCode());
	            });
	            invoiceMapper.insertInvoiceLines(header.getLines()); // bulk insert
	        }
	    }
	
	
}
