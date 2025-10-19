package com.yedam.erp.mapper.common;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprovalMapper {
	// 전표 상태 + 전자결재
	  int updateJournalStatus(Map<String, Object> param);
	//구독 + 전자결제
	 int updateDocumentSign(Map<String, Object> param);

}