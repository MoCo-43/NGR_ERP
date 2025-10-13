package com.yedam.erp.service.impl.account;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.PaymentMapper;
import com.yedam.erp.service.account.MoneyService;
import com.yedam.erp.vo.account.InvoiceHeaderVO;
import com.yedam.erp.vo.account.PaymentApplyVO;
import com.yedam.erp.vo.account.PaymentHeaderVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final PaymentMapper moneyMapper;

    @Override
    public List<InvoiceHeaderVO> getUnpaidInvoices(String type, String fromDate, String toDate, String searchCus, Long companyCode) {
    	
        return moneyMapper.selectUnpaidInvoices(type, fromDate, toDate, searchCus,companyCode);
    }

    
    @Override
    @Transactional
    public void savePayment(PaymentHeaderVO payment) {
        // INSERT 실행 시 MyBatis가 payCode를 미리 세팅
    	moneyMapper.insertPaymentHeader(payment);

        // 2이미 payment.getPayCode()에 NEXTVAL 값이 들어 있음
        Long payCode = payment.getPayCode();

        // 3상세 등록 및 미결 차감
        if (payment.getApplies() != null && !payment.getApplies().isEmpty()) {
            for (PaymentApplyVO apply : payment.getApplies()) {
                apply.setPayCode(payCode);
                moneyMapper.insertPaymentApply(apply);
                moneyMapper.updateInvoiceUnpaid(apply);
            }
       }
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