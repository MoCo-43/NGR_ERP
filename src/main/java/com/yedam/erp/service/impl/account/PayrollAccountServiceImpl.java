package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.PayrollAccountMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.PayrollAccountService;
import com.yedam.erp.vo.account.PayrollJournalLineVO;
import com.yedam.erp.vo.account.PayrollJournalVO;
import com.yedam.erp.vo.account.PayrollLineVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollAccountServiceImpl implements PayrollAccountService {

    private final PayrollAccountMapper paymentMapper;

    @Override
    public List<PayrollLineVO> getDeptJournalLines(int payrollNo,Long companyCode) {
        return paymentMapper.selectDeptJournalLines(payrollNo, companyCode);
        
    }
    @Override
    @Transactional
    public String registerPayrollJournal(PayrollJournalVO vo) {
    	 // ✅ 1. 전표번호 생성 (P-YYYYMMDD-###)
        String histNo = paymentMapper.getNextPayrollNo();
        vo.setHistNo(histNo);
        vo.setCompanyCode(SessionUtil.companyId());

        // ✅ 2. 헤더 insert (histCode 자동 생성)
        paymentMapper.insertPayrollHist(vo);
        Long histCode = vo.getHistCode();

        // ✅ 3. 라인 insert
        int lineNo = 1;
        if (vo.getLines() != null) {
            for (PayrollJournalLineVO line : vo.getLines()) {
                line.setHistCode(histCode);
                line.setLineNo(lineNo++);
                line.setCompanyCode(vo.getCompanyCode());
                paymentMapper.insertPayrollJrnHist(line);
            }
        }

        // ✅ 4. PAYROLL 테이블 전표번호 & 일자 업데이트
        paymentMapper.updatePayrollJournalInfo(vo);

        return histNo;
    }
}