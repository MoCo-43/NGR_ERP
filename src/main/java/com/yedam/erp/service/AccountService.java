package com.yedam.erp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yedam.erp.vo.account.accountVO;
import com.yedam.erp.vo.account.invoiceVO;

public interface AccountService {
	
	// 계정과목 리스트
	List<accountVO> accountList(String category);
	
	//상태 변경
	int updateYN(String acctCode);
	
	//엑셀 업로드
	List<accountVO> parseExcel(MultipartFile file) throws Exception;
	
	//엑셀 인서트
	public int bulkInsert(List<accountVO> accounts);
	
	//매입 매출전표 
	List<invoiceVO> selectInvoice();
	
}
