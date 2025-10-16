package com.yedam.erp.mapper.Biz;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

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
  // ==주문서 헤더==
  int insertPOHeader(PoInsertVO pvo);
  // ==주문서 디테일==
  int insertPODetails(PoInsertVO pvo);
  
  // 주문서 이력 조회
  List<PurchaseOrderVO> getPOHistory(Long companyCode);
  // 품목 조회
  List<ProductCodeVO> getProducts(Long companyCode);
  // 거래처 조회
  List<CustomerVO> getCustomers(Long companyCode);
  // 출하지시서 조회
  List<DeliveryOrderVO> selectDo(Long companyCode);
  // 출하지시서 헤더
  int insertDO(DoInsertVO dovo);
  // 거래처관리 조회
  List<CustomerVO> getCustomerManagement(Long companyCode);
  // 거래처관리 등록
  int insertCustomer(CustomerVO cvo);
  // 거래처관리 수정
  int updateCustomerByCode(CustomerVO cvo);
  // 거래처여신 조회
      /**
     * 회사별 거래처 여신현황 조회
     * @param companyCode 필수 (예: "1001")
     * @param monthBase 기준월의 1일(예: 2025-10-01). null이면 SYSDATE 기준.
     * @return 여신현황 리스트
     */
  List<CustomerCreditVO> selectCrdMaster(Long companyCode, @Param("monthBase") LocalDate monthBase);

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

}
