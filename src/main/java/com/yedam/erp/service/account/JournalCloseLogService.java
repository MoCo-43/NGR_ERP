package com.yedam.erp.service.account;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.erp.vo.account.JournalCloseLogVO;


public interface JournalCloseLogService {
	 void insertLog(Long companyCode, List<String> jrnNoList, String actionType, String loginUser, String remarks,String status);
	    List<JournalCloseLogVO> getLogList(Long companyCode);
	    List<JournalCloseLogVO> getLogByJrn(Long companyCode, String jrnNo);
}