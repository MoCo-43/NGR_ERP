package com.yedam.erp.mapper.Biz;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.Biz.PoInsertVO;
import com.yedam.erp.vo.Biz.ProductCodeVO;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

@Mapper
public interface BizMapper {

  // 테스트 주문서전체조회
  List<PurchaseOrderVO> getAllPO();
  // 주문서 조회
  List<PurchaseOrderVO> selectPO();
  // 주문서 등록
  int insertPO(PoInsertVO pvo);
  // 주문서 이력 조회
  List<PurchaseOrderVO> getPOHistory();
  // 품목 조회
  List<ProductCodeVO> getProducts();
}
