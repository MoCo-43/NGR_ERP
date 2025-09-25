package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.PayDeductCodeMapper;
import com.yedam.erp.service.hr.PayDeductCodeService;
import com.yedam.erp.vo.hr.PayDeductCodeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayDeductCodeServiceImpl implements PayDeductCodeService {

    private final PayDeductCodeMapper mapper;

    @Override
    public List<PayDeductCodeVO> getList(String useYn, String deductName) {
        PayDeductCodeVO param = new PayDeductCodeVO();
        param.setUseYn(useYn);
        param.setDeductName(deductName);
        return mapper.selectPayDeductList(param);
    }

    @Override
    public PayDeductCodeVO get(String deductCode) {
        return mapper.selectPayDeduct(deductCode);
    }

    @Override
    @Transactional
    public boolean create(PayDeductCodeVO vo) {
        // 기본값 세팅 (useYn 안 들어오면 Y)
        if (vo.getUseYn() == null || vo.getUseYn().isBlank()) {
            vo.setUseYn("Y");
        }
        return mapper.insertPayDeduct(vo) == 1;
    }

    @Override
    @Transactional
    public boolean update(PayDeductCodeVO vo) {
        return mapper.updatePayDeduct(vo) == 1;
    }

    @Override
    @Transactional
    public boolean changeUseYn(String deductCode, String useYn) {
        PayDeductCodeVO vo = new PayDeductCodeVO();
        vo.setDeductCode(deductCode);
        vo.setUseYn(useYn);
        return mapper.updateUseYn(vo) == 1;
    }
}
