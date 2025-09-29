package com.yedam.erp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.common.ApprovalMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.ApprovalService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalMapper approvalMapper;

    @Override
    public int updateStatus(String docType, List<String> ids, String status, Long companyCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        param.put("status", status);
        param.put("companyCode", companyCode);

        switch (docType.toLowerCase()) {
            case "journal":   return approvalMapper.updateJournalStatus(param);
            // case "purchase": return approvalMapper.updatePurchaseStatus(param);
            default: throw new IllegalArgumentException("지원하지 않는 문서 유형: " + docType);
        }
    }

    @Override
    public int approveWithSign(String docType, List<String> ids, String signPath, Long companyCode, String approver) {
        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        param.put("status", "closed");
        param.put("signPath", signPath);
        param.put("companyCode", companyCode);
        param.put("approver", approver);

        switch (docType.toLowerCase()) {
            case "journal":   return approvalMapper.updateJournalStatus(param);
            // case "purchase": return approvalMapper.updatePurchaseStatus(param);
            default: throw new IllegalArgumentException("지원하지 않는 문서 유형: " + docType);
        }
    }
}
