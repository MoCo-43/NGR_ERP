package com.yedam.erp.service.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.account.AutoJournalMapper;
import com.yedam.erp.vo.account.AutoJournalRuleVO;
import com.yedam.erp.vo.account.JournalVO;
import com.yedam.erp.vo.account.PaymentHeaderVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoJournalService {


    private final AutoJournalMapper autoJournalMapper;

    public List<JournalVO> createFromPayment(PaymentHeaderVO payment) {
        List<JournalVO> list = new ArrayList<>();

        Map<String, Object> param = new HashMap<>();
        param.put("moduleType", "PAYMENT");
        param.put("payType", payment.getPayType());
        param.put("payMethod", payment.getMethod()); // optional

        List<AutoJournalRuleVO> rules = autoJournalMapper.selectRulesByPayType(param);
        if (rules.isEmpty()) {
            throw new IllegalStateException("자동분개 규칙이 등록되어 있지 않습니다. (PAY_TYPE=" + payment.getPayType() + ")");
        }

        for (AutoJournalRuleVO rule : rules) {
            list.add(JournalVO.builder()
                    .acctCode(rule.getAcctCode())
                    .dcType(rule.getDcType())
                    .amount(payment.getAmount())
                    .remarks(rule.getRemarks())
                    .cusCode(payment.getCusCode())
                    .status("open")
                    .createdAt(new Date())
                    .createdBy("SYSTEM")
                    .companyCode(payment.getCompanyCode())
                    .build());
        }
        System.out.println(list);
        return list;
    }
}