package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

@Mapper
public interface InvoiceMapper {

    // 헤더 목록 조회
    List<InvoiceHeaderVO> selectInvoiceHeaders(Long companyCode);

    // 특정 전표 라인 조회
    List<InvoiceLineVO> selectInvoiceLinesByHeader(String invoiceCode);

    // 전표번호 생성
    String selectNextInvoiceNo();

    // 헤더 insert
    int insertInvoiceHeader(InvoiceHeaderVO header);

    // 라인 insert (bulk insert)
    int insertInvoiceLines(List<InvoiceLineVO> lines);
}