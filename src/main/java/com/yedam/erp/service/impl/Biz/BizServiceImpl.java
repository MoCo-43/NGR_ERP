package com.yedam.erp.service.impl.Biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.Biz.BizMapper;
import com.yedam.erp.service.Biz.BizService;
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

@Service
public class BizServiceImpl implements BizService {

	@Autowired
	BizMapper bizMapper;

	// 테스트 전체조회
	@Override
	public List<PurchaseOrderVO> getAllPO(Long companyCode) {
		return bizMapper.getAllPO(companyCode);
	}

	// 주문서 전체조회
	@Override
	public List<JoinPoVO> selectPO(Long companyCode) {
		return bizMapper.selectPO(companyCode);
	}

  // 주문서 등록
  @Override
	@Transactional
	public Long createPo(PoInsertVO pvo) {
        // 방어코드: 필수값
      if (pvo.getPoDetails() == null || pvo.getPoDetails().isEmpty()) {
        throw new IllegalArgumentException("상세 품목이 1건 이상 필요합니다.");
    }

        // 헤더 insert(여기서 vo.poId 세팅됨)
        bizMapper.insertPOHeader(pvo);

        // 디테일 insert
        bizMapper.insertPODetails(pvo);

        return pvo.getPoId();
    }

	// 주문서 조회
    @Override
    public List<PoHistoryVO> getPoHistory(Long companyCode) {
        return bizMapper.getPoHistory(companyCode);
    }

  // 주문서 상태변경
  public int poStatusUpdate(List<String> poCodes, String poStatus) {
    int cnt = 0;
    for(int i=0; i<poCodes.size(); i++) {
      
      cnt += bizMapper.poStatusUpdate(poCodes.get(i), poStatus);
      
    }

   return cnt;
}


	// 품목 조회
	@Override
	public List<ProductCodeVO> getProducts(Long companyCode) {
		return bizMapper.getProducts(companyCode);
	}
	// 거래처 조회
	@Override
	public List<CustomerVO> getCustomers(Long companyCode) {
		return bizMapper.getCustomers(companyCode);
	}

	// 출하지시서 전체조회
	@Override
	public List<DeliveryOrderVO> selectDo(Long companyCode) {
		return bizMapper.selectDo(companyCode);
	}

  // 출하지시서 등록
  @Override
	@Transactional
  public Long createDo(DoInsertVO dovo) {

        // 방어코드: 필수값
      if (dovo.getDodetails() == null || dovo.getDodetails().isEmpty()) {
        throw new IllegalArgumentException("상세 목이 1건 이상 필요합니다.");
    }

        // 헤더 insert(여기서 vo.poId 세팅됨)
        bizMapper.insertDOHeader(dovo);

        // 디테일 insert
        bizMapper.insertDODetails(dovo);

        return dovo.getDoNo();
  }
  


	
	// 거래처 및 여신 조회
	@Override
	public List<CustomerVO> getCustomerManagement(Long companyCode) {
		return bizMapper.getCustomerManagement(companyCode);
	}

  // 거래처 관리 및 여신등록
  @Override
  @Transactional
  public int insertCustomerWithCredit(CustomerVO cvo) {
    if (cvo.getCompanyCode() == null) {
      throw new IllegalArgumentException("companyCode is required");
    }

    // 1) CUS_CODE 생성
    String cusCode = bizMapper.nextCusCode();
    cvo.setCusCode(cusCode);

    // 2) 거래처 저장
    int affected = bizMapper.insertCustomer(cvo);
    if (affected != 1) {
      throw new IllegalStateException("insertCustomer failed");
    }

    // 3) 여신 저장 (옵션)
    CreditVO cr = cvo.getCredit();
    if (cr != null) {
      cr.setCusCode(cusCode);                                 // ★★ 꼭 필요
      cr.setCompanyCode(cvo.getCompanyCode());
      if (cr.getCActiveStatus() == null || cr.getCActiveStatus().isBlank()) {
        cr.setCActiveStatus("Y");
      }
      affected += bizMapper.insertCredit(cr);
    }

    return affected; // 총 영향행 (1 또는 2)
  }


  
	// 거래처관리 수정  updateCustomerByCode
    @Override
    @Transactional
    public int updateCustomerByCusCode(CustomerVO cvo) {
        int updated = bizMapper.updateCustomerByCusCode(cvo);
        return updated;
    }

  // 거래처여신관리 수정
    @Override
    @Transactional
    public int updateCreditByCusCode(CreditVO cvo) {
      int cupdated = bizMapper.updateCreditByCusCode(cvo);
      return cupdated;
    }



  // 여신현황조회
  @Override
  public List<CustomerCreditVO> selectCrdMaster(Long companyCode) {
        return bizMapper.selectCrdMaster(companyCode);
    }

  // 여신관리페이지
  // 1. 거래처별 관리 페이지
  // @Override
  // public List<CreditExposureVO> list(Long companyCode, String grade, String name, String manager) {
  //   return bizMapper.selectExposureList(companyCode, grade, name, manager);
  // }

  // @Override
  // public CreditExposureVO get(Long companyCode, Long cusCode) {
  //   return bizMapper.selectCreditMaster(companyCode, cusCode);
  // }

  // @Override
  // @Transactional
  // public void save(CreditExposureVO vo, String user) {
  //   // 간단 검증/기본값
  //   if (vo.getCompanyCode() == null || vo.getCusCode() == null) {
  //     throw new IllegalArgumentException("companyCode/cusCode is required");
  //   }

  //   // TODO: 변경 전후 이력 저장이 필요하면 여기서 'select → history insert → upsert' 순으로 처리
  //   bizMapper.upsertCreditMaster(vo);
  // }

  // @Override
  // @Transactional
  // public void deactivate(Long companyCode, Long cusCode, String user) {
  //   bizMapper.deactivateCredit(companyCode, cusCode);
  //   // TODO: 비활성화 이력 필요 시 별도 history insert
  // }

  
  // 여신등급정책조회
  @Override
  public 
  List<CreditGradeVO>getCreditGrade(Long companyCode) {
    return bizMapper.getCreditGrade(companyCode);
  }
}
