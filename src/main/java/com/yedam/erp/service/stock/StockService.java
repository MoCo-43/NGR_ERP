package com.yedam.erp.service.stock;

import java.util.List;

import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

public interface StockService {

	// 제품등록
	public int insertProduct(ProductVO product); 
	// 제품리스트
	public List<ProductVO> productAll();
		
	// 거래처 조회
	public List<PartnerVO> customerAll();
	
	// 발주걔획 등록
	public void insertOrderPlan(OrderPlanVO orderPlan); // 마스터
	
	// 발주계획 조회
	List<OrderPlanVO> getOrderPlans();
	
}
