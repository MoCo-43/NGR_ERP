package com.yedam.erp.mapper.stock;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.stock.PartnerVO;
import com.yedam.erp.vo.stock.ProductVO;

@Mapper
public interface StockMapper {

	// 제품등록
	public int insertProduct(ProductVO product);
	
	// 거래처 조회
	public List<PartnerVO> customerAll();
	
	
}
