package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.account.PaymentMapper;
import com.yedam.erp.service.account.PaymentService;
import com.yedam.erp.vo.account.PaymentLineVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentLineVO> getDeptJournalLines(String yearMonth, String deptCode,Long companyCode) {
        return paymentMapper.selectDeptJournalLines(yearMonth, deptCode, companyCode);
    }
}