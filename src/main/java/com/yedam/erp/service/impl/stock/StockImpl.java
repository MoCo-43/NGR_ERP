package com.yedam.erp.service.impl.stock;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.stock.StockMapper;
import com.yedam.erp.service.stock.StockService;
import com.yedam.erp.vo.stock.OrderPlanVO;
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


	@Override
	public int insertOrderPlan(OrderPlanVO orderPlan) {
		
		return mapper.insertOrderPlan(orderPlan);
	}


	@Override
	public List<ProductVO> productAll() {
		
		return mapper.productAll();
	}

}
