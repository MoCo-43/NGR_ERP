package com.yedam.erp.service.impl.stock;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.stock.StockMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.stock.StockService;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StockImpl implements StockService{

	
	final private StockMapper mapper;
	
	
	@Override
	public int insertProduct(ProductVO product) {
		
		return mapper.insertProduct(product);
	}


	@Override
	public List<PartnerVO> customerAll(Map<String, Object> params) {
	    return mapper.customerAll(params);
	}

	@Transactional
	@Override
	public void insertOrderPlan(OrderPlanVO plan) {
		mapper.insertOrderPlan(plan);
        if(plan.getDetails() != null) {
            for(OrderPlanDetailVO item : plan.getDetails()) {
                item.setXpCode(plan.getXpCode()); // 마스터 PK -> 디테일 FK
                System.out.println("Detail XP_CODE: " + item.getXpCode()); // 디버깅용
                mapper.insertOrderPlanDetail(item);
            }
        }
	}


	@Override
	public List<ProductVO> productAll(Long compCode) {
		
		return mapper.productAll(compCode);
	}


	@Override
	public List<OrderPlanVO> getOrderPlans() {
		return mapper.selectOrderPlans();
	}


	@Override
	public List<OrderDetailVO> selectOrderDetailsByXpCode(String str) {
		return mapper.selectOrderDetailsByXpCode(str);
	}


	@Override
	public OrderVO selectOrderByXpCode(String str) {
		return mapper.selectOrderByXpCode(str);
	}


	@Override
	public CompanyVO selectComp(Long compId) {
		return mapper.selectComp(compId);
	}


	@Override
	public CustomerVO selectCutomer(String cusCode) {
		return mapper.selectCutomer(cusCode);
	}


	@Override
	public List<OrderVO> getOrderList(Long compId) {
		return mapper.getOrderList(compId);
	}


	@Override
	public List<OrderDetailVO> getOrderDetailByOrderCode(String orderCode) {
		return mapper.getOrderDetailByOrderCode(orderCode);
	}

	@Transactional
	@Override
	public void insertOrderReq(OrderVO order) {
		mapper.insertOrderReq(order);
		System.out.println(order.getDetails());
        if(order.getDetails() != null) {
            for(OrderDetailVO item : order.getDetails()) {
                //item.setXpCode(order.getXpCode()); // 마스터 PK -> 디테일 FK
            	item.setOrderCode(order.getOrderCode());// 마스터 PK -> 디테일 FK
                System.out.println("Detail ORDER_CODE: " + item.getOrderCode()); // 디버깅용
                mapper.insertOrderDetail(item);
            }
        }
		
	}


	@Override
	public List<InvenVO> getIcList(Long companyCode) {
		return mapper.getIcList(companyCode);
	}


	@Override
	public List<InvenDetailVO> getIcDetailList(Long companyCode, String selectedRow) {
		return mapper.getIcDetailList(companyCode, selectedRow);
	}

	@Transactional
	@Override
	public void insertInvenClosing(String empId , Long compCode) {
		// TODO Auto-generated method stub
		System.out.println("[결산생성] 월말 재고결산 데이터 생성 시작");
		System.out.println("[데이터체크] 이월된 결산 데이터 조회 시작");
		 int exists = mapper.checkThisMonthSettlement();
	        if (exists > 0) {
	        	System.out.println("[데이터체크] 이월된 결산 데이터 조회 완료");
	            System.out.println("[결산생성] 이번 달 결산 데이터가 이미 존재합니다.");
	            return;
	        }
	    System.out.println("[데이터체크] 이월된 결산 데이터 조회 완료");
	    System.out.println("[결산생성] 이번 달 결산 데이터가 존재하지 않습니다.");
	    System.out.println("empId , compCode check : "+empId+", "+compCode);
	    InvenVO instance = new InvenVO();
	    instance.setCompanyCode(compCode);
	    instance.setEmpId(empId);
		mapper.insertInvenClosing(instance); // 마스터 처리
		
		InvenDetailVO instDatail = new InvenDetailVO();
		instDatail.setCompanyCode(compCode);
		instDatail.setEmpId(empId);
		instDatail.setIcCode(instance.getIcCode());
		
		mapper.insertInvenClosingDetail(instDatail); // 상세 처리
		
		System.out.println("[결산생성] 재고결산 데이터 생성 완료");
	}
	
	


	@Override
	public List<InboundVO> getInboundList(Long companyCode) {
		return mapper.getInboundList(companyCode);
	}


	@Override
	public List<InboundVO> getInboundDetail(String selectedRow) {
		return mapper.getInboundDetail(selectedRow);
	}


	@Override
	public List<OrderDetailVO> getOrderDetailByXpCode(String orderCode) {
		return mapper.getOrderDetailListByOrderCode(orderCode);
	}

	@Transactional
	@Override
	public void insertInbound(List<InboundVO> details) {
        for (InboundVO detail : details) {
            mapper.insertInbound(detail); // 단일 INSERT
        }
    }


	@Override
	public List<ComOrderVO> getDeliveryOrderList() {
		return mapper.getDeliveryOrderList();
	}


	@Override
	public List<ComOrderDetailVO> getComOrderDetailList(String doCode) {
		return mapper.getComOrderDetailList(doCode);
	}


	@Transactional
	@Override
	public void insertOutbound(OutboundHeaderVO payload) {
		
		System.out.println("Header doCode=" + payload.getDoCode());
		System.out.println("Header dueDate=" + payload.getDueDate());
		
		mapper.insertOutbound(payload);
		System.out.println(payload.getDetails());
		if(payload.getDetails() != null) {
			for(OutboundVO item : payload.getDetails()) {
            	item.setOutbHeaderCode(payload.getOutbHeaderCode());// 마스터 PK -> 디테일 FK
                System.out.println("Detail ORDER_CODE: " + item.getOutbHeaderCode()); // 디버깅용
                mapper.insertOutboundDetail(item);
                
                
                // 제품별 LOT 처리
                Long qty = item.getQty();
                Long outboundNo = item.getOutboundNo();
                List<InboundVO> inboundLots = mapper.selectAvailableLots(item.getProductCode());
                for(InboundVO lot : inboundLots) {
                    if(qty <= 0L) break;
                    
                    Long qtyToOut = Math.min(qty, lot.getQty());
                    LotoutboundVO lotOut = new LotoutboundVO();
                    lotOut.setLotCode(lot.getLotCode());
                    lotOut.setLotOutAmt(qtyToOut);
                    lotOut.setCompanyCode(item.getCompanyCode());
                    lotOut.setOutboundNo(outboundNo);
                    mapper.insertLotOutbound(lotOut);      
                }
            }
		}
	}// END OF insertOutbound


	@Override
	public List<OutboundHeaderVO> getOutboundList() {
		return mapper.getDeliveryNote();
	}


	@Override
	public List<OutboundVO> selectOutboundByOutbHeaderCode(String outbHeaderCode , String doCode) {
		return mapper.selectOutboundByOutbHeaderCode(outbHeaderCode, doCode);
	}


	@Override
	public int updateSign(InvenVO payload) {
		
		return mapper.updateIcSignDataByIcCode(payload);
		
	}


	@Override
	public String getProductFileNameByProductCodeAndCompCode(Long compCode, String productCode) {
		// 제품 선택시 이미지 명 불러오기
		return mapper.getProductFileNameByProductCodeAndCompCode(productCode, compCode);
	}


	@Override
	public List<InboundVO> getProductDetailRefInbound(Long compCode, String productCode) {
		return mapper.getProductDetailRefInbound(compCode , productCode);
	}


	@Override
	public List<InboundVO> findInboundList(Map<String, Object> params) {
		return mapper.selectInboundList(params);
	}




}
