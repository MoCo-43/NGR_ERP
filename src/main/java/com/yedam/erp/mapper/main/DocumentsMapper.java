package com.yedam.erp.mapper.main;

import com.yedam.erp.vo.main.DocumentsVO;

public interface DocumentsMapper {

	int insertSign(DocumentsVO vo);
    DocumentsVO selectLatestSignature(Long matNo);
}
