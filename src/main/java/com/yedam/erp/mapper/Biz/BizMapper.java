package com.yedam.erp.mapper.Biz;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.Biz.CustomerCreditVO;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.Biz.DeliveryOrderVO;
import com.yedam.erp.vo.Biz.DoInsertVO;
import com.yedam.erp.vo.Biz.JoinPoVO;
import com.yedam.erp.vo.Biz.PoInsertVO;
import com.yedam.erp.vo.Biz.ProductCodeVO;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

@Mapper
public interface BizMapper {

  // 테스트 주문서전체조회
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
  // 출하지시서 등록
  int insertDO(DoInsertVO dovo);
  // 거래처관리 조회
  List<CustomerVO> getCustomerManagement(Long companyCode);
  // 거래처관리 등록
  int insertCustomer(CustomerVO cvo);
  // 거래처관리 수정
  int updateCustomerByCode(CustomerVO cvo);
  // 거래처여신 조회
  List<CustomerCreditVO> selectCrdMaster(Long companyCode);
}
