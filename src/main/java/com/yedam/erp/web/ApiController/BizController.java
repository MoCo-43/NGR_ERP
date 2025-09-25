package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.Biz.BizService;
import com.yedam.erp.vo.Biz.CustomerVO;
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
    return service.getAllPO();
  }

  // 주문서 조회
  @GetMapping("/polist")
  public List<PurchaseOrderVO> selectPO() {
      return service.selectPO();
  }
  
  // 주문서 등록 (Axios JSON)
  @PostMapping(value = "/poinsert", consumes = "application/json")
  public ResponseEntity<Integer> insertPO(@RequestBody PoInsertVO pvo) {
      int result = service.insertPO(pvo);
      return ResponseEntity.ok(result);
  }

  // 주문서 이력 조회
  @GetMapping("/pohistory")
  public List<PurchaseOrderVO> getPOHistory() {
      return service.getPOHistory();
  }

  // 품목 조회
  @GetMapping("/productcode")
  public List<ProductCodeVO> getProducts() {
      return service.getProducts();
  }

  // 거래처 조회
  @GetMapping("/customercode")
  public List<CustomerVO> getCustomers() {
      return service.getCustomers();
  }
}
