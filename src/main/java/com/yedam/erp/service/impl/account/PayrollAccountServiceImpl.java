package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.account.PayrollAccountMapper;
import com.yedam.erp.service.account.PayrollAccountService;
import com.yedam.erp.vo.account.PayrollLineVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollAccountServiceImpl implements PayrollAccountService {

    private final PayrollAccountMapper paymentMapper;

    @Override
    public List<PayrollLineVO> getDeptJournalLines(String yearMonth, String deptCode,Long companyCode) {
        return paymentMapper.selectDeptJournalLines(yearMonth, deptCode, companyCode);
    }
}