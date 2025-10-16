package com.yedam.erp.service.impl.account;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.JournalMapper;
import com.yedam.erp.mapper.account.PaymentMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.AutoJournalService;
import com.yedam.erp.service.account.MoneyService;
import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.JournalVO;
import com.yedam.erp.vo.account.PaymentApplyVO;
import com.yedam.erp.vo.account.PaymentHeaderVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final PaymentMapper moneyMapper;
    private final JournalMapper journalMapper;
    private final AutoJournalService autoJournalService;
    
    @Override
    public List<InvoiceHeaderVO> getUnpaidInvoices(String type, String fromDate, String toDate, String searchCus, Long companyCode) {
    	
        return moneyMapper.selectUnpaidInvoices(type, fromDate, toDate, searchCus,companyCode);
    }

    
    /**
     * 자금전표 저장 + 자동분개 + 일반전표 반영
     */
    @Override
    @Transactional
    public List<JournalVO> savePaymentAndAutoJournal(PaymentHeaderVO payment) {
        // 1️⃣ 자금전표 헤더 저장
    	moneyMapper.insertPaymentHeader(payment);
        Long payCode = payment.getPayCode();

        // 2️⃣ 상세 등록 및 미결 차감
        if (payment.getApplies() != null && !payment.getApplies().isEmpty()) {
            for (PaymentApplyVO apply : payment.getApplies()) {
                apply.setPayCode(payCode);
                moneyMapper.insertPaymentApply(apply);
                moneyMapper.updateInvoiceUnpaid(apply);
            }
        }
        System.out.println("🔥 AutoJournalService 호출됨 : " + payment.getPayType() + "/" + payment.getMethod());
        // 3️⃣ 자동분개 생성
        List<JournalVO> journals = autoJournalService.createFromPayment(payment);

        // 4️⃣ 전표번호 발급
        String jrnNo = journalMapper.selectNextJrnNo();
        int idx = 1;

        // 5️⃣ 일반전표 등록 (한 줄씩 insert)
        for (JournalVO j : journals) {
        	j.setCreatedBy(SessionUtil.empName());
            j.setJrnNo(jrnNo);
            j.setLineNo((long) idx++);
            j.setJrnDate(new java.util.Date());
            j.setStatus("open");
            j.setCompanyCode(payment.getCompanyCode());
            j.setCusCode(payment.getCusCode());
            journalMapper.insertJournal(j);
        }

        // 6️⃣ 자금전표 상태 업데이트
        moneyMapper.updatePostedFlag(Map.of(
            "payCode", payCode,
            "companyCode", payment.getCompanyCode(),
            "postedFlag", "Y"
        ));
        
        return journals;
    }
    @Override
    public List<PaymentHeaderVO> getPaymentList(Long companyCode) {
        return moneyMapper.getPaymentList(companyCode);
    }
    
    
    @Override
    @Transactional
    public void updatePaymentPosted(Long payCode, Long companyCode, String postedFlag) {
    	moneyMapper.updatePostedFlag(Map.of(
            "payCode", payCode,
            "companyCode", companyCode,
            "postedFlag", postedFlag
        ));
    }
}