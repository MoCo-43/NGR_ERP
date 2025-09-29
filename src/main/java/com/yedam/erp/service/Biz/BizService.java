package com.yedam.erp.service.Biz;

import java.util.List;

import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.Biz.DeliveryOrderVO;
import com.yedam.erp.vo.Biz.JoinPoVO;
import com.yedam.erp.vo.Biz.PoInsertVO;
import com.yedam.erp.vo.Biz.ProductCodeVO;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

public interface BizService {

  // 테스트 전체목록 조회
  List<PurchaseOrderVO> getAllPO(Long companyCode);

  // 주문서 조회
  List<JoinPoVO> selectPO(Long companyCode);

  // 주문서 등록
  int insertPO(PoInsertVO pvo);

  // 주문서 이력 조회
  List<PurchaseOrderVO> getPOHistory(Long companyCode);

  // 품목 조회
  List<ProductCodeVO> getProducts(Long companyCode);
  
  // 거래처 조회
  List<CustomerVO> getCustomers(Long companyCode);

  // 출하지시서 조회
  List<DeliveryOrderVO> selectDo(Long companyCode);
}
