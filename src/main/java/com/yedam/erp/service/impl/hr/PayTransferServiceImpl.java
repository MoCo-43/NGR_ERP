package com.yedam.erp.service.impl.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.PayTransferMapper;
import com.yedam.erp.service.hr.PayTransferService;
import com.yedam.erp.vo.hr.PayTransferVO;

@Service
public class PayTransferServiceImpl implements PayTransferService {

    @Autowired
    private PayTransferMapper payTransferMapper;


    @Override
    public List<PayTransferVO> getTransferViewFromConfirmed(Long companyCode, String payYm) {
        Map<String, Object> param = new HashMap<>();
        param.put("companyCode", companyCode);
        param.put("payYm", payYm);
        return payTransferMapper.selectTransferViewFromConfirmed(param);
    }
}
