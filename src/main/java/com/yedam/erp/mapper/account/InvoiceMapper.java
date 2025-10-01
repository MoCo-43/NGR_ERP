package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.InvoiceLineVO;

@Mapper
public interface InvoiceMapper {

	List<InvoiceHeaderVO> selectInvoiceHeaders(Long companyCode);

	List<InvoiceLineVO> selectInvoiceLinesByHeader(@Param("invoiceCode") Long invoiceCode, @Param("companyCode") Long companyCode);
	
	// 전표번호 채번
    String selectNextInvoiceNo();

    // 헤더+라인 한방 INSERT
    void insertInvoiceWithLines(InvoiceHeaderVO header);
    
    // 전자세금
    InvoiceHeaderVO selectInvoiceHeaderById(@Param("companyCode") Long companyCode,@Param("invoiceCode")Long invoiceCode);

    // 매출매입전표 상태 Y로변경
    void updatePostedFlag(@Param("companyCode") Long companyCode,
            @Param("invoiceCode") Long invoiceCode,
            @Param("postedFlag") String postedFlag);

}