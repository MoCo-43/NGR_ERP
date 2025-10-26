package com.yedam.erp.service.Biz;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.Biz.CreditGradeVO;
import com.yedam.erp.vo.Biz.CreditVO;
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
  List<PoHistoryVO> getPoHistory(Long companyCode);


  // 주문서 상태변경(승인)
  int poStatusUpdate(List<String> poCodes, String poStatus);
  // 주문서 상태변경(입금완료)
  /*
   * 선택한 PO들에 대해'입금완료'로 변경된 건수
  */
  int markPaidAndIncreaseCredit(List<String> poCodes);

  // 품목 조회
  List<ProductCodeVO> getProducts(Long companyCode);
  
  // 거래처 조회
  List<CustomerVO> getCustomers(Long companyCode);

  // 출하지시서 조회
  List<DeliveryOrderVO> selectDo(Long companyCode);

  // 출하지시서 등록
  Long createDo(DoInsertVO dovo);

  // 거래처 및 여신 조회
  List<CustomerVO> getCustomerManagement(Long companyCode);

  // 거래처 관리 및 여신등록
  int insertCustomerWithCredit(CustomerVO cvo);


  // 거래처관리 수정
  int updateCustomerByCusCode(CustomerVO cvo);
  // 거래처여신관리 수정
  int updateCreditByCusCode(CreditVO cvo);



  // 여신현황조회
  List<CustomerCreditVO> selectCrdMaster(Long companyCode);

  // 여신관리페이지
  // 1. 거래처별 관리 페이지
  // List<CreditExposureVO> list(Long companyCode, String grade, String name, String manager);

  // CreditExposureVO get(Long companyCode, Long cusCode);

  // void save(CreditExposureVO vo, String user);   // 등록/수정 (필요 시 이력 저장 포함)

  // void deactivate(Long companyCode, Long cusCode, String user);


  // 여신등급정책조회
  List<CreditGradeVO>getCreditGrade(Long companyCode);
  
  // 여신등급정책 일괄 업데이트(행별 업데이트, 없으면 insert) */
  int updateCreditPolicies(Long companyCode, List<CreditGradeVO> list);

}

