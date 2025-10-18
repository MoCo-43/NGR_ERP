package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.InvoiceMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.InvoiceService;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;
import com.yedam.erp.vo.main.CompanyVO;

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
	    public InvoiceHeaderVO getInvoiceHeader(Long companyCode, Long invoiceCode) {
	        return invoiceMapper.selectInvoiceHeaderById(companyCode, invoiceCode);
	    }


	    @Override
	    public List<InvoiceLineVO> getInvoiceLines(Long invoiceCode,Long companyCode) {
	        return invoiceMapper.selectInvoiceLinesByHeader(invoiceCode,companyCode);
	    }

	    @Transactional
	    @Override
	    public void saveInvoiceWithLines(InvoiceHeaderVO header) {
	        // 회사 코드 세팅
	        header.setCompanyCode(SessionUtil.companyId());

	        // 전표번호 채번
	        String nextNo = invoiceMapper.selectNextInvoiceNo();
	        header.setInvoiceNo(nextNo);

	        // 라인번호 세팅 (1,2,3…)
	        if (header.getLines() != null) {
	            int idx = 1;
	            for (InvoiceLineVO line : header.getLines()) {
	                line.setLineNo(idx++);
	            }
	        }

	        // Mapper 호출
	        invoiceMapper.insertInvoiceWithLines(header);
	    }
	    
	    // 전자 세금 계산서 호출
	    @Override
	    public InvoiceHeaderVO getInvoiceWithLines(Long companyCode, Long invoiceCode) {
	        InvoiceHeaderVO header = invoiceMapper.selectInvoiceHeaderById(companyCode, invoiceCode);
	        List<InvoiceLineVO> lines = invoiceMapper.selectInvoiceLinesByHeader(invoiceCode, companyCode);
	        header.setLines(lines);
	        return header;
	    }
	    
	    // 매출매입전표 상태변경
	    @Override
	    @Transactional
	    public void updatePostedFlag(Long companyCode, Long invoiceCode, String postedFlag) {
	        invoiceMapper.updatePostedFlag(companyCode, invoiceCode, postedFlag);
	    }

		@Override
		public CompanyVO selectCompanyInfo(Long companyCode) {
			return invoiceMapper.selectCompanyInfo(companyCode);
		}

		@Override
		public CustomerVO selectCustomerInfo(String cusCode, Long companyCode) {
			return invoiceMapper.selectCustomerInfo(cusCode, companyCode);
		}
	    
	    
	    	
}
