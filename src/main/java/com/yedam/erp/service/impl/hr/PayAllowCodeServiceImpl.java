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
public class PayAllowCodeServiceImpl implements PayAllowCodeService {

    private final PayAllowCodeMapper mapper;

    @Override
    public List<PayAllowCodeVO> getAllowList(Long companyCode) {
        return mapper.selectAllowList(companyCode);
    }

    @Override
    public PayAllowCodeVO getAllow(PayAllowCodeVO vo) {
        return mapper.selectAllow(vo);
    }

    @Override
    @Transactional
    public int addAllow(PayAllowCodeVO vo) {
        return mapper.insertAllow(vo);
    }

    @Override
    @Transactional
    public int editAllow(PayAllowCodeVO vo) {
        return mapper.updateAllow(vo);
    }

	@Override
	@Transactional
	public int changeUseYn(PayAllowCodeVO vo) {
	    return mapper.updateUseYn(vo);
	}
}
