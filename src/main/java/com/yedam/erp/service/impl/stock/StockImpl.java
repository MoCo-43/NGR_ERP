package com.yedam.erp.service.impl.stock;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.stock.StockMapper;
import com.yedam.erp.service.stock.StockService;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.stock.OrderDetailVO;
import com.yedam.erp.vo.stock.OrderPlanDetailVO;
import com.yedam.erp.vo.stock.OrderPlanVO;
import com.yedam.erp.vo.stock.OrderVO;
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
	public List<PartnerVO> customerAll() {
		// TODO Auto-generated method stub
		return mapper.customerAll();
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
	public List<ProductVO> productAll() {
		
		return mapper.productAll();
	}


	@Override
	public List<OrderPlanVO> getOrderPlans() {
		// TODO Auto-generated method stub
		return mapper.selectOrderPlans();
	}


	@Override
	public List<OrderDetailVO> selectOrderDetailsByXpCode(String str) {
		// TODO Auto-generated method stub
		return mapper.selectOrderDetailsByXpCode(str);
	}


	@Override
	public OrderVO selectOrderByXpCode(String str) {
		// TODO Auto-generated method stub
		return mapper.selectOrderByXpCode(str);
	}


	@Override
	public CompanyVO selectComp(Long compId) {
		// TODO Auto-generated method stub
		return mapper.selectComp(compId);
	}


	@Override
	public CustomerVO selectCutomer(String cusCode) {
		// TODO Auto-generated method stub
		return mapper.selectCutomer(cusCode);
	}

}
