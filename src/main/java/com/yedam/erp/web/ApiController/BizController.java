package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.Biz.BizService;
import com.yedam.erp.vo.Biz.CustomerVO;
import com.yedam.erp.vo.Biz.DeliveryOrderVO;
import com.yedam.erp.vo.Biz.JoinPoVO;
import com.yedam.erp.vo.Biz.PoDetailVO;
import com.yedam.erp.vo.Biz.PoInsertVO;
import com.yedam.erp.vo.Biz.ProductCodeVO;
import com.yedam.erp.vo.Biz.PurchaseOrderVO;



@RestController
@RequestMapping("/api/biz")
public class BizController {

  @Autowired
  BizService service;

  // 전체 주문서 목록 조회 API
  @GetMapping("/list")
  public List<PurchaseOrderVO> list() {
    Long companyCode = SessionUtil.companyId();
    return service.getAllPO(companyCode);
  }

  // 주문서 조회
  @GetMapping("/polist")
  public List<JoinPoVO> selectPO() {
    Long companyCode = SessionUtil.companyId();
      return service.selectPO(companyCode);
  }
  
  // 주문서 등록 처리
  @PostMapping(value = "/poinsert", consumes = "application/json")
  public ResponseEntity<Integer> insertPO(@RequestBody PoInsertVO pvo) {

    // 세션에서 회사코드 꺼내오기
    Long companyCode = SessionUtil.companyId();

    // VO에 세팅
    pvo.setCompanyCode(companyCode);
    for (PoDetailVO detail : pvo.getPoDetails()) {
        detail.setCompanyCode(companyCode);
    }

    int result = service.insertPO(pvo);
    return ResponseEntity.ok(result);  
  }

  // 주문서 이력 조회
  @GetMapping("/pohistory")
  public List<PurchaseOrderVO> getPOHistory() {
    Long companyCode = SessionUtil.companyId();
      return service.getPOHistory(companyCode);
  }

  // 품목 조회
  @GetMapping("/productcode")
  public List<ProductCodeVO> getProducts() {
    Long companyCode = SessionUtil.companyId();
      return service.getProducts(companyCode);
  }

  // 거래처 조회
  @GetMapping("/customercode")
  public List<CustomerVO> getCustomers() {
    Long companyCode = SessionUtil.companyId();
      return service.getCustomers(companyCode);
  }

  // 출하지시서 조회
  @GetMapping("/dolist")
  public List<DeliveryOrderVO> getDoList() {
    Long companyCode = SessionUtil.companyId();
      return service.selectDo(companyCode);
  }
}
