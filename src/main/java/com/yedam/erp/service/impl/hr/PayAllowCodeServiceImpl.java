package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.PayAllowCodeMapper;
import com.yedam.erp.service.hr.PayAllowCodeService;
import com.yedam.erp.vo.hr.PayAllowCodeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PayAllowCodeServiceImpl implements PayAllowCodeService {

    private final PayAllowCodeMapper mapper;

    @Override
    public List<PayAllowCodeVO> getList(PayAllowCodeVO param) {
        return mapper.selectPayAllowList(param);
    }

    @Override
    public PayAllowCodeVO get(String allowCode) {
        return mapper.selectPayAllow(allowCode);
    }

    @Override
    @Transactional
    public boolean insert(PayAllowCodeVO vo) {
        return mapper.insertPayAllow(vo) == 1;
    }

    @Override
    @Transactional
    public boolean update(PayAllowCodeVO vo) {
        return mapper.updatePayAllow(vo) == 1;
    }

    @Override
    @Transactional
    public boolean changeUseYn(String allowCode, String useYn) {
        PayAllowCodeVO vo = new PayAllowCodeVO();
        vo.setAllowCode(allowCode);
        vo.setUseYn(useYn);
        return mapper.updateUseYn(vo) == 1;
    }
}
