package com.yedam.erp.service.stock;

import java.util.List;

import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.stock.InboundVO;
import com.yedam.erp.vo.stock.InvenDetailVO;
import com.yedam.erp.vo.stock.InvenVO;
import com.yedam.erp.vo.stock.OrderDetailVO;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.OrderVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

public interface StockService {

	// 제품등록
	public int insertProduct(ProductVO product); 
	// 제품리스트
	public List<ProductVO> productAll(Long CompCode);
		
	// 거래처 조회
	public List<PartnerVO> customerAll(Long CompCode);
	
	// 발주계획 등록
	public void insertOrderPlan(OrderPlanVO orderPlan); // 마스터
	
	// 발주계획 조회
	List<OrderPlanVO> getOrderPlans();
	
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
	public List<OrderDetailVO> getOrderDetailByXpCode(String orderCode);
	
	// 발주 등록
	public void insertOrderReq(OrderVO order); // 마스터
	
	// 결산 마스터 조회
	public List<InvenVO> getIcList(Long companyCode);
	
	// 결산 디테일 조회
	public List<InvenDetailVO> getIcDetailList(Long companyCode , String seletedRow);
	
	// 결산 등록
	public void insertInvenClosing(InvenVO inven);
	
	// 입고조회
	public List<InboundVO> getInboundList(Long companyCode);
	public List<InboundVO> getInboundDetail(String selectedRow);
	
	// 입고 등록
	public void insertInbound(List<InboundVO> details);
}
