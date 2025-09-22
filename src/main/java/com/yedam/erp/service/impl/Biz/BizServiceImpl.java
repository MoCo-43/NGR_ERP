package com.yedam.erp.service.impl.Biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.Biz.BizMapper;
import com.yedam.erp.service.Biz.BizService;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

@Service
public class BizServiceImpl implements BizService {

	@Autowired
	BizMapper bizMapper;

	// 테스트 전체조회
	@Override
	public List<PurchaseOrderVO> getAllPO() {
		return bizMapper.getAllPO();
	}

	// 주문서 전체조회
	@Override
	public List<PurchaseOrderVO> selectPO() {
		return bizMapper.selectPO();
	}

	// 주문서 입력
	@Override
	public int insertPO(PurchaseOrderVO vo) {
		return bizMapper.insertPO(vo);
	}

}
