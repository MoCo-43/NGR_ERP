package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

@Mapper
public interface InvoiceMapper {
    List<InvoiceHeaderVO> selectInvoiceHeaders(Long companyCode);
    List<InvoiceLineVO> selectInvoiceLinesByHeader(String invoiceCode);

    int insertInvoiceHeader(InvoiceHeaderVO vo);
    int insertInvoiceLine(InvoiceLineVO vo);
}