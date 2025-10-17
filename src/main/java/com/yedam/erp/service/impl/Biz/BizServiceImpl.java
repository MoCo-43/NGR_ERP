package com.yedam.erp.service.impl.Biz;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.Biz.BizMapper;
import com.yedam.erp.service.Biz.BizService;
import com.yedam.erp.vo.Biz.CreditExposureVO;
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
    public List<PoHistoryVO> getPOHistory(Long companyCode) {
        return bizMapper.getPOHistory(companyCode);
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
	public int insertDO(DoInsertVO dovo) {
		return bizMapper.insertDO(dovo);
	}	
	
	// 거래처관리 조회
	@Override
	public List<CustomerVO> getCustomerManagement(Long companyCode) {
		return bizMapper.getCustomerManagement(companyCode);
	}

	// 거래처관리 등록
	@Override	
	public int insertCustomer(CustomerVO cvo) {
		return bizMapper.insertCustomer(cvo);
	}

	// 거래처관리 수정  updateCustomerByCode
    @Override
    @Transactional
    public int updateCustomerByCode(CustomerVO cvo) {
        int updated = bizMapper.updateCustomerByCode(cvo);
        return updated;
    }

	// 거래처여신관리 조회
    @Override
    public List<CustomerCreditVO> selectCrdMaster(Long companyCode, LocalDate monthBase) {
        // monthBase가 null이면 이번달 1일로 세팅 (DB 쿼리에서도 SYSDATE로 안전장치 해둠)
        if (monthBase == null) {
            monthBase = LocalDate.now().withDayOfMonth(1);
        }
        return bizMapper.selectCrdMaster(companyCode, monthBase);
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
}
