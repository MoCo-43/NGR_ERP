package com.yedam.erp.service.stock;

import java.util.List;

import com.yedam.erp.vo.stock.CustomerVO;
import com.yedam.erp.vo.stock.ProductVO;

public interface StockService {

	// 제품등록
	public int insertProduct(ProductVO product); 
	
	// 거래처 조회
	public List<CustomerVO> customerAll();
	
	
}
