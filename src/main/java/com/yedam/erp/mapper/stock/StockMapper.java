package com.yedam.erp.mapper.stock;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.stock.OrderPlanDetailVO;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

@Mapper
public interface StockMapper {

	// 제품등록
	public int insertProduct(ProductVO product);
	// 제품리스트
	public List<ProductVO> productAll();
	
	// 거래처 조회
	public List<PartnerVO> customerAll();
	
	// 발주계획 등록
	public void insertOrderPlan(OrderPlanVO orderPlan); // 마스터
	public int insertOrderPlanDetail(OrderPlanDetailVO item); // 상세
	
	// 발주계획 조회
	public List<OrderPlanVO> selectOrderPlans();
	List<OrderPlanDetailVO> selectOrderPlanDetailsByXpCode(String xpCode);
	
}
