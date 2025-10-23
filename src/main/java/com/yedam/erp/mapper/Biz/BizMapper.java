package com.yedam.erp.mapper.Biz;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
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

@Mapper
public interface BizMapper {

  // 테스트 주문서전체조회
  List<PurchaseOrderVO> getAllPO(Long companyCode);
  // 주문서 조회
  List<JoinPoVO> selectPO(Long companyCode);

  // 주문서 등록
  // ==주문서 헤더==
  int insertPOHeader(PoInsertVO pvo);
  // ==주문서 디테일==
  int insertPODetails(PoInsertVO pvo);
  
  // 주문서 이력 조회
  List<PoHistoryVO> getPoHistory(Long companyCode);

  // 주문서 상태변경
  int poStatusUpdate(String poCode, String poStatus);

  // 품목 조회
  List<ProductCodeVO> getProducts(Long companyCode);
  // 거래처 조회
  List<CustomerVO> getCustomers(Long companyCode);
  // 출하지시서 조회
  List<DeliveryOrderVO> selectDo(Long companyCode);

  // 출하지시서 등록
  // ==출하지시서 헤더==
  int insertDOHeader(DoInsertVO dovo);
  // ==출하지시서 디테일==
  int insertDODetails(DoInsertVO dovo);

  // 거래처 및 여신 조회
  List<CustomerVO> getCustomerManagement(Long companyCode);

  // 거래처코드 생성
  String nextCusCode();
  // 거래처 관리 및 여신등록
  int insertCustomer(CustomerVO cvo);
  // 거래처별 여신등록
  int insertCredit(CreditVO cvo);

  /*
   *   @Transactional
  public void insertCustomerWithCredit(CustomerVO cvo) {
    // 1) 고객코드 생성
    String cusCode = bizMapper.nextCusCode();
    cvo.setCusCode(cusCode);

    // 2) 거래처 등록
    bizMapper.insertCustomer(cvo);

    // 3) 여신 등록 (있을 경우)
    if (cvo.getCredit() != null) {
      CreditVO cr = cvo.getCredit();
      cr.setCusCode(cusCode);
      cr.setCompanyCode(cvo.getCompanyCode());
      if (cr.getActiveStatus() == null) cr.setActiveStatus("Y");
      bizMapper.insertCredit(cr);
    }
  }
   * 
   * 
   */


  // 거래처관리 수정
  int updateCustomerByCode(CustomerVO cvo);
  // 거래처여신관리 수정
  int updateCreditByCode(CreditVO cvo);


  

  // 여신현황조회
  List<CustomerCreditVO> selectCrdMaster(Long companyCode);

  // 여신관리페이지
  // 1. 거래처별 관리 페이지
  // 1) 현황 그리드
  // List<CreditExposureVO> selectExposureList(@Param("companyCode") Long companyCode,
  //                                           @Param("grade") String grade,
  //                                           @Param("name") String name,
  //                                           @Param("manager") String manager);

  // // 2) 단건 상세
  // CreditExposureVO selectCreditMaster(@Param("companyCode") Long companyCode,
  //                                   @Param("cusCode") Long cusCode);

  // // 3) 등록/수정
  // int upsertCreditMaster(CreditExposureVO vo);

  // // 4) 비활성화
  // int deactivateCredit(@Param("companyCode") Long companyCode,
  //                      @Param("cusCode") Long cusCode);


  // 여신등급정책조회
  List<CreditGradeVO>getCreditGrade(Long companyCode);
}
