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

    private final PayrollAccountMapper payrollAccountMapper; // ✅ 명칭 통일

    /**
     * 부서별 급여항목 → 회계 전표라인 변환
     */
    @Override
    public List<PayrollLineVO> getDeptJournalLines(int payrollNo, Long companyCode) {
        return payrollAccountMapper.selectDeptJournalLines(payrollNo, companyCode);
    }

    /**
     * 급여 확정 시 전표 헤더 + 라인 등록 (트랜잭션 처리)
     */
    @Override
    @Transactional
    public String registerPayrollJournal(PayrollJournalVO vo) {

        // ✅ 1. 전표번호 생성 (예: P-20251016-001)
        String histNo = payrollAccountMapper.getNextPayrollNo();
        vo.setHistNo(histNo);
        vo.setCompanyCode(SessionUtil.companyId());

        // ✅ 2. 헤더 등록 (histCode는 selectKey로 자동 세팅됨)
        payrollAccountMapper.insertPayrollHist(vo);
        Long histCode = vo.getHistCode();

        // ✅ 3. 상세라인 등록
        int lineNo = 1;
        if (vo.getLines() != null && !vo.getLines().isEmpty()) {
            for (PayrollJournalLineVO line : vo.getLines()) {

                // 필수 필드 세팅
                line.setHistCode(histCode);
                line.setLineNo(lineNo++);
                line.setCompanyCode(vo.getCompanyCode());

                // ✅ 금액 누락 방지: amount null → 0
                if (line.getAmount() == null) {
                    line.setAmount(java.math.BigDecimal.ZERO);
                }

                payrollAccountMapper.insertPayrollJrnHist(line);
            }
        }

        // ✅ 4. PAYROLL 테이블 전표번호/일자 업데이트
        payrollAccountMapper.updatePayrollJournalInfo(vo);

        return histNo;
    }
}
