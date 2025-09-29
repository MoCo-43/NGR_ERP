package com.yedam.erp.service;

import java.util.List;

public interface ApprovalService {

	  int updateStatus(String docType, List<String> ids, String status, Long companyCode);
	  int approveWithSign(String docType, List<String> ids, String signPath, Long companyCode, String approver);
}