package com.yedam.erp.mapper.stock;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.stock.OrderDetailVO;
import com.yedam.erp.vo.stock.OrderPlanDetailVO;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.OrderVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

@Mapper
public interface StockMapper {

	// 제품등록
	public int insertProduct(ProductVO product);
	// 제품리스트
	public List<ProductVO> productAll(Long CompCode);
	
	// 거래처 조회
	public List<PartnerVO> customerAll(Long compCode);
	
	// 발주계획 등록
	public void insertOrderPlan(OrderPlanVO orderPlan); // 마스터
	public int insertOrderPlanDetail(OrderPlanDetailVO item); // 상세
	
	// 발주계획 조회
	public List<OrderPlanVO> selectOrderPlans();
	List<OrderPlanDetailVO> selectOrderPlanDetailsByXpCode(String xpCode);
	
	// 발주서에 조회될 발주 마스터
	public OrderVO selectOrderByXpCode(String xpCode);
	
	// 발주서에 조회될 발주 디테일
	List<OrderDetailVO>selectOrderDetailsByXpCode(String xpCode);
	
	// 세션 회사 정보 조회
	public CompanyVO selectComp(Long compId);
	
	// 발주서 거래처 정보 조회
	public CustomerVO selectCutomer(String cusCode);
	
	// 발주 조회
	public List<OrderVO> getOrderList(Long compId);
	public List<OrderDetailVO> getOrderDetailByOrderCode(String orderCode); 
	
}
