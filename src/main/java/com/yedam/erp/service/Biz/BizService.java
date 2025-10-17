package com.yedam.erp.service.Biz;

import java.time.LocalDate;
import java.util.List;

import com.yedam.erp.vo.Biz.CustomerCreditVO;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.Biz.DeliveryOrderVO;
import com.yedam.erp.vo.Biz.DoInsertVO;
import com.yedam.erp.vo.Biz.JoinPoVO;
import com.yedam.erp.vo.Biz.PoHistoryVO;
import com.yedam.erp.vo.Biz.PoInsertVO;
import com.yedam.erp.vo.Biz.ProductCodeVO;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;

public interface BizService {

  // 테스트 전체목록 조회
  List<PurchaseOrderVO> getAllPO(Long companyCode);

  // 주문서 조회
  List<JoinPoVO> selectPO(Long companyCode);

  // 주문서 등록
  Long createPo(PoInsertVO pvo);

  // 주문서 이력 조회
  List<PoHistoryVO> getPOHistory(Long companyCode);

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
  List<CustomerCreditVO> selectCrdMaster(Long companyCode, LocalDate monthBase);

  // 여신관리페이지
  // 1. 거래처별 관리 페이지
  // List<CreditExposureVO> list(Long companyCode, String grade, String name, String manager);

  // CreditExposureVO get(Long companyCode, Long cusCode);

  // void save(CreditExposureVO vo, String user);   // 등록/수정 (필요 시 이력 저장 포함)

  // void deactivate(Long companyCode, Long cusCode, String user);


}

