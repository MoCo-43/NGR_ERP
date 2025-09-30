package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.InvoiceMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.InvoiceService;
import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

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
    public List<InvoiceLineVO> getInvoiceLines(String invoiceCode) {
        return invoiceMapper.selectInvoiceLinesByHeader(invoiceCode);
    }

    @Override
    public int createInvoiceHeader(InvoiceHeaderVO vo) {
        vo.setCompanyCode(SessionUtil.companyId()); 
        return invoiceMapper.insertInvoiceHeader(vo);
    }

    @Override
    public int createInvoiceLine(InvoiceLineVO vo) {
        vo.setCompanyCode(SessionUtil.companyId()); 
        return invoiceMapper.insertInvoiceLine(vo);
    }
    /**
     * ✅ 전표 + 라인 전체 저장 (트랜잭션)
     */
    @Transactional
    @Override
    public void createInvoiceWithLines(InvoiceHeaderVO header, List<InvoiceLineVO> lines) {
        Long companyId = SessionUtil.companyId();
        header.setCompanyCode(companyId);
        invoiceMapper.insertInvoiceHeader(header);

        if (lines != null) {
            for (InvoiceLineVO line : lines) {
                line.setInvoiceCode(header.getInvoiceCode()); // FK 세팅
                line.setCompanyCode(companyId);
                invoiceMapper.insertInvoiceLine(line);
            }
        }
    }
    
    
    
}
