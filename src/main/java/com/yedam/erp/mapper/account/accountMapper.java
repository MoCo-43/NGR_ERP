package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.account.accountVO;
import com.yedam.erp.vo.account.invoiceVO;

@Mapper
public interface accountMapper {
	
	// 계정 과목
	 List<accountVO> selectAll(String category);
	 int updateYN(String acctCode);
	 int bulkInsert(List<accountVO> accounts);
	 
	 // 매입 매출 전표
	 List<invoiceVO> selectInvoice();
}
