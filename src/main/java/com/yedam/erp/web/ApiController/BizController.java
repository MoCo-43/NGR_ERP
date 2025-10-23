package com.yedam.erp.web.ApiController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
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
  public ResponseEntity<?> createPO(@RequestBody PoInsertVO pvo) {

    // 세션에서 회사코드 꺼내오기
    Long companyCode = SessionUtil.companyId();
    String creater = SessionUtil.empName();
    Long poId = service.createPo(pvo);

    // 회사코드적용
    pvo.setCompanyCode(companyCode);
    // 작성자적용
    pvo.setCreater(creater);
    // 디테일 poId전달
    pvo.setPoId(poId);

    return ResponseEntity.ok().body(pvo);

  }

  // 주문서 이력 조회
    @GetMapping("/pohistory")
    public List<PoHistoryVO> getPoHistory() {
        Long companyCode = SessionUtil.companyId();
        return service.getPoHistory(companyCode);
    }


  // 주문서 상태변경
  @PutMapping("/postatus")
  public int poStatusUpdate(@RequestBody Map<String, Object> poMap) {
      List<String> poCodes = (List<String>) poMap.get("poCodes");
      String poStatus = poMap.get("poStatus").toString();
      return service.poStatusUpdate(poCodes, poStatus);
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

  // 출하지시서 등록 처리
  @PostMapping(value = "/doinsert", consumes = "application/json")
  public ResponseEntity<?> createDO(@RequestBody DoInsertVO dovo) {

    // 세션에서 회사코드 꺼내오기
    Long companyCode = SessionUtil.companyId();
    Long doNo = service.createDo(dovo);

    // 회사코드적용
    dovo.setCompanyCode(companyCode);
    // 디테일 poId전달
    dovo.setDoNo(doNo);

    return ResponseEntity.ok().body(dovo);

  }



  // 거래처 및 여신 조회
  @GetMapping("/mngcustomer")
  public List<CustomerVO> getCustomerManagement() {
    Long companyCode = SessionUtil.companyId();
    return service.getCustomerManagement(companyCode);
  }


  // 거래처, 거래처별 여신등록
  @PostMapping(value="/mngcustomer")
  public ResponseEntity<Map<String, Object>> insCusCredit(@RequestBody CustomerVO cvo) {
    Long companyCode = SessionUtil.companyId();
    cvo.setCompanyCode(companyCode);
    int result = service.insertCustomerWithCredit(cvo);

    Map<String, Object> body = new HashMap<>();
    body.put("affected", result);
    body.put("cusCode", cvo.getCusCode()); // ★★ 서비스에서 세팅된 신규 코드
    return ResponseEntity.ok(body);
  }



  // 거래처 수정
  @PutMapping("/mngcustomer/{cusCode}")
      public ResponseEntity<?> updateCustomerByCode(
              @PathVariable String cusCode,
              @RequestBody CustomerVO cvo
      ) {
          cvo.setCusCode(cusCode);

          int updated = service.updateCustomerByCode(cvo);
          if (updated == 0) {
              return ResponseEntity.notFound().build();
          }
          return ResponseEntity.noContent().build(); // 또는 ok(vo)
    }

  // 거래처 여신 수정
  @PutMapping("/mngcuscredit/{cusCode}")
  public ResponseEntity<?> updateCreditByCode(
      @PathVariable String cusCode,
      @RequestBody CreditVO cvo
  ) {
          cvo.setCusCode(cusCode);

          int updated = service.updateCreditByCode(cvo);
          if (updated == 0) {
              return ResponseEntity.notFound().build();
          }
          return ResponseEntity.noContent().build(); // 또는 ok(vo)
      }

  // 여신현황조회
     @GetMapping("/crdlist")
     public List<CustomerCreditVO> selectCrdMaster() {
       Long companyCode = SessionUtil.companyId();
         return service.selectCrdMaster(companyCode);
     }

  // 여신관리페이지
  // 1. 거래처별 관리 페이지
    // 1) 거래처별 여신 현황(그리드)
    //    GET /api/biz/crdlist?grade=&name=&manager=
    // @GetMapping("/crdlist")
    // public List<CreditExposureVO> list(
    //         @RequestParam(required = false) String grade,
    //         @RequestParam(required = false) String name,
    //         @RequestParam(required = false) String manager
    // ) {
    //     Long companyCode = SessionUtil.companyId();
    //     return service.selectExposureList(companyCode, grade, name, manager);
    // }

    // // 2) 단건 조회 (수정 모달용)
    // //    GET /api/biz/credit/{cusCode}
    // @GetMapping("/credit/{cusCode}")
    // public ResponseEntity<CreditMasterVO> getByCusCode(@PathVariable String cusCode) {
    //     Long companyCode = SessionUtil.companyId();
    //     CreditMasterVO vo = service.selectCreditMaster(companyCode, cusCode);
    //     if (vo == null) return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok(vo);
    // }

    // // 3) 등록 (신규)
    // //    POST /api/biz/credit
    // @PostMapping("/credit")
    // public ResponseEntity<?> insert(@RequestBody CreditMasterVO vo) {
    //     Long companyCode = SessionUtil.companyId();
    //     String user = SessionUtil.loginId(); // 로그인 사용자 식별자(사번/아이디 등)
    //     vo.setCompanyCode(companyCode);

    //     int inserted = service.insertCreditMaster(vo, user);
    //     if (inserted > 0) {
    //         return ResponseEntity.status(HttpStatus.CREATED).build();
    //     }
    //     return ResponseEntity.badRequest().body("INSERT FAILED");
    // }

    // // 4) 수정 (cusCode 기준)
    // //    PUT /api/biz/credit/{cusCode}
    // @PutMapping("/credit/{cusCode}")
    // public ResponseEntity<?> updateByCusCode(
    //         @PathVariable String cusCode,
    //         @RequestBody CreditMasterVO vo
    // ) {
    //     Long companyCode = SessionUtil.companyId();
    //     String user = SessionUtil.loginId();
    //     vo.setCompanyCode(companyCode);
    //     vo.setCusCode(cusCode);

    //     int updated = service.updateCreditMaster(vo, user);
    //     if (updated == 0) return ResponseEntity.notFound().build();
    //     return ResponseEntity.noContent().build();
    // }

    // // 5) 비활성화(중지) 또는 삭제 대용
    // //    DELETE /api/biz/credit/{cusCode}
    // @DeleteMapping("/credit/{cusCode}")
    // public ResponseEntity<?> deactivate(@PathVariable String cusCode) {
    //     Long companyCode = SessionUtil.companyId();
    //     String user = SessionUtil.loginId();

    //     int changed = service.deactivateCredit(companyCode, cusCode, user);
    //     if (changed == 0) return ResponseEntity.notFound().build();
    //     return ResponseEntity.noContent().build();
    // }

    // // (옵션) 상태만 변경: PATCH /api/biz/credit/{cusCode}/status  { "status": "SUSPEND" }
    // @PatchMapping("/credit/{cusCode}/status")
    // public ResponseEntity<?> changeStatus(
    //         @PathVariable String cusCode,
    //         @RequestBody Map<String, String> body
    // ) {
    //     Long companyCode = SessionUtil.companyId();
    //     String user = SessionUtil.loginId();
    //     String status = body.getOrDefault("status", "SUSPEND");

    //     int changed = service.updateCreditStatus(companyCode, cusCode, status, user);
    //     if (changed == 0) return ResponseEntity.notFound().build();
    //     return ResponseEntity.noContent().build();
    // }

  // 여신등급정책조회
  @GetMapping("/crdgradelist")
  public List<CreditGradeVO>getCreditGrade() {
      Long companyCode = SessionUtil.companyId();
    return service.getCreditGrade(companyCode);
  }

}
