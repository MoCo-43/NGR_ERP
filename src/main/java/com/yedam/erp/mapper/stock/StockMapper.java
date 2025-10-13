package com.yedam.erp.mapper.stock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.stock.ComOrderDetailVO;
import com.yedam.erp.vo.stock.ComOrderVO;
import com.yedam.erp.vo.stock.InboundVO;
import com.yedam.erp.vo.stock.InvenDetailVO;
import com.yedam.erp.vo.stock.InvenVO;
import com.yedam.erp.vo.stock.LotoutboundVO;
import com.yedam.erp.vo.stock.OrderDetailVO;
import com.yedam.erp.vo.stock.OrderPlanDetailVO;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.OrderVO;
import com.yedam.erp.vo.stock.OutboundHeaderVO;
import com.yedam.erp.vo.stock.OutboundVO;
import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

@Mapper
public interface StockMapper {

	// 제품등록
	public int insertProduct(ProductVO product);
	// 제품리스트
	public List<ProductVO> productAll(Long CompCode);
	
	// 거래처 조회
	public List<PartnerVO> customerAll(Map<String, Object> params);
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
	public List<OrderDetailVO> getOrderDetailListByOrderCode(@Param("orderCode") String orderCode); // 입고등록용 발주 상세 조회
	
	// 발주 등록
	public void insertOrderReq(OrderVO order); // 마스터
	public int insertOrderDetail(OrderDetailVO item); // 상세
	
	// 결산 마스터 조회
	public List<InvenVO> getIcList(Long companyCode);
	
	// 결산 디테일 조회
	public List<InvenDetailVO> getIcDetailList(Long companyCode ,  @Param("icCode")String selectedRow);
	
	// 결산 등록
	public void insertInvenClosing(InvenVO inven);// 마스터
	public int insertInvenClosingDetail(InvenDetailVO item);// 상세
	
	// 입고조회
	public List<InboundVO> getInboundList(@Param("compCode") Long companyCode);
	public List<InboundVO> getInboundDetail(@Param("lotCode") String selectedRow);
	
	// 입고 등록
	public void insertInbound(InboundVO inbound);
	
	
	// 출고 등록
	public void insertOutbound(OutboundHeaderVO payload);// 헤더
	public int insertOutboundDetail(OutboundVO item);// 상세
	public int insertLotOutbound(LotoutboundVO lot); // 제품별 LOT 출고내역
	public List<InboundVO> selectAvailableLots(@Param("productCode") String productCode);// LOT 조회용
	public int updateInboundQty(@Param("lotCode")String lotCode , @Param("qty")Long qty);
	
	// 출하 지시시 조회
	public List<ComOrderVO> getDeliveryOrderList();
	public List<ComOrderDetailVO> getComOrderDetailList(@Param("doCode") String doCode);
}
