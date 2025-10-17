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
	//공제 전체조회
	@Override
	public List<PayDeductCodeVO> getDeductList(Long companyCode) {
		return mapper.selectDeductList(companyCode);
	}
	//공제 단건조회
	@Override
	public PayDeductCodeVO getDeduct(PayDeductCodeVO vo) {
		return mapper.selectDeduct(vo);
	}
	// 공제 등록
	@Override
	@Transactional
	public int addDeduct(PayDeductCodeVO vo) {
		return mapper.insertDeduct(vo);
	}
	//공제 수정
	@Override
	@Transactional
	public int editDeduct(PayDeductCodeVO vo) {
		return mapper.updateDeduct(vo);
	}
	//사용여부
	@Override
	@Transactional
	public int changeUseYn(PayDeductCodeVO vo) {
		return mapper.updateUseYn(vo);
	}
}
