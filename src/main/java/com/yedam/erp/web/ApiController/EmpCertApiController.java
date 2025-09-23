package com.yedam.erp.web.ApiController;

import java.util.*;
import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.EmpCertService;
import com.yedam.erp.vo.hr.EmpCertVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/cert")
public class EmpCertApiController {

    private final EmpCertService service;

    // ===== 목록 조회: empId 기준 =====
    @GetMapping
    public List<EmpCertVO> list(@RequestParam("empId") String empId) {
        EmpCertVO param = new EmpCertVO();
        param.setEmpId(empId);
        return service.selectEmpCertList(param);
    }

    // ===== 등록 (JSON) =====
    // 프론트: Content-Type: application/json
    @PostMapping
    public Map<String, Object> create(@RequestBody EmpCertVO vo) {
        Map<String, Object> res = new HashMap<>();
        if (vo.getEmpId() == null || vo.getEmpId().isEmpty()) {
            res.put("success", false);
            res.put("message", "사번(empId)이 필요합니다.");
            return res;
        }
        boolean ok = service.insertEmpCert(vo);
        res.put("success", ok);
        return res;
    }

    // ===== (옵션) 등록 (form-urlencoded) =====
    // 예전 폼 방식도 병행하고 싶으면 사용: /api/hr/cert/form 로 호출
    @PostMapping(path = "/form", consumes = "application/x-www-form-urlencoded")
    public Map<String, Object> createByForm(EmpCertVO vo) {
        Map<String, Object> res = new HashMap<>();
        if (vo.getEmpId() == null || vo.getEmpId().isEmpty()) {
            res.put("success", false);
            res.put("message", "사번(empId)이 필요합니다.");
            return res;
        }
        boolean ok = service.insertEmpCert(vo);
        res.put("success", ok);
        return res;
    }

    // ===== 삭제 =====
    @DeleteMapping("/{certNo}")
    public Map<String, Object> delete(@PathVariable("certNo") Long certNo) {
        EmpCertVO vo = new EmpCertVO();
        vo.setCertNo(certNo);
        boolean ok = service.deleteEmpCert(vo);

        Map<String, Object> res = new HashMap<>();
        res.put("success", ok);
        return res;
    }
}
