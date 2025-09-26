package com.yedam.erp.service.impl.Biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.Biz.BizMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.Biz.BizService;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.Biz.JoinPoVO;
import com.yedam.erp.vo.Biz.PoInsertVO;
import com.yedam.erp.vo.Biz.ProductCodeVO;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

@Service
public class BizServiceImpl implements BizService {

	@Autowired
	BizMapper bizMapper;

	// 테스트 전체조회
	@Override
	public List<PurchaseOrderVO> getAllPO(Long companyCode) {
		return bizMapper.getAllPO(companyCode);
	}

	// 주문서 전체조회
	@Override
	public List<JoinPoVO> selectPO(Long companyCode) {
		return bizMapper.selectPO(companyCode);
	}

	// 주문서 입력
	@Override
	public int insertPO(PoInsertVO pvo) {
		return bizMapper.insertPO(pvo);
	}

	// 주문서 이력 조회
	@Override
	public List<PurchaseOrderVO> getPOHistory(Long companyCode) {
		return bizMapper.getPOHistory(companyCode);
	}
	// 품목 조회
	@Override
	public List<ProductCodeVO> getProducts(Long companyCode) {
		return bizMapper.getProducts(companyCode);
	}
	// 거래처 조회
	@Override
	public List<CustomerVO> getCustomers(Long companyCode) {
		return bizMapper.getCustomers(companyCode);
	}
}
