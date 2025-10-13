package com.yedam.erp.service.impl.stock;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.stock.StockMapper;
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
	public List<ProductVO> productAll(Long CompCode) {
		
		return mapper.productAll(CompCode);
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
		// TODO Auto-generated method stub
		return mapper.getIcList(companyCode);
	}


	@Override
	public List<InvenDetailVO> getIcDetailList(Long companyCode, String selectedRow) {
		// TODO Auto-generated method stub
		return mapper.getIcDetailList(companyCode, selectedRow);
	}


	@Override
	public void insertInvenClosing(InvenVO inven) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<InboundVO> getInboundList(Long companyCode) {
		// TODO Auto-generated method stub
		return mapper.getInboundList(companyCode);
	}


	@Override
	public List<InboundVO> getInboundDetail(String selectedRow) {
		// TODO Auto-generated method stub
		return mapper.getInboundDetail(selectedRow);
	}


	@Override
	public List<OrderDetailVO> getOrderDetailByXpCode(String orderCode) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return mapper.getDeliveryOrderList();
	}


	@Override
	public List<ComOrderDetailVO> getComOrderDetailList(String doCode) {
		// TODO Auto-generated method stub
		return mapper.getComOrderDetailList(doCode);
	}


	@Transactional
	@Override
	public void insertOutbound(OutboundHeaderVO payload) {
		// TODO Auto-generated method stub
		mapper.insertOutbound(payload);
		System.out.println(payload.getDetails());
		if(payload.getDetails() != null) {
			for(OutboundVO item : payload.getDetails()) {
            	item.setOutbHeaderCode(payload.getOutbHeaderCode());// 마스터 PK -> 디테일 FK
                System.out.println("Detail ORDER_CODE: " + item.getOutbHeaderCode()); // 디버깅용
                mapper.insertOutboundDetail(item);
                
                
                // 제품별 LOT 처리
                Long qty = item.getQty();
                
                List<InboundVO> inboundLots = mapper.selectAvailableLots(item.getProductCode());
                for(InboundVO lot : inboundLots) {
                    if(qty <= 0L) break;
                    
                    Long qtyToOut = Math.min(qty, lot.getQty());
                    LotoutboundVO lotOut = new LotoutboundVO();
                    lotOut.setLotCode(lot.getLotCode());
                    lotOut.setLotOutAmt(qtyToOut);
                    lotOut.setCompanyCode(item.getCompanyCode());
                    lotOut.setOutboundNo(item.getOutboundNo());
                    mapper.insertLotOutbound(lotOut);

                    // 입고 잔여수량 업데이트
                    mapper.updateInboundQty(lot.getLotCode(), lot.getQty() - qtyToOut);
                    qty -= qtyToOut;
             
                }
            }
		}
	}





}
