package com.yedam.erp.service;

import com.yedam.erp.vo.main.DocumentsVO;


public interface DocumentService {
	int insertSign(DocumentsVO vo);
	//최선정보 전자서명사인
    DocumentsVO selectLatestSignature(Long matNo);
}
