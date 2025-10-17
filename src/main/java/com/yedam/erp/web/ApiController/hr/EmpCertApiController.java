package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.hr.EmpCertService;
import com.yedam.erp.vo.hr.EmpCertVO;

import lombok.RequiredArgsConstructor;

/**
 * 자격증 API Controller
 * - 목록(사번 기준), 등록(JSON), 삭제
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/cert")
public class EmpCertApiController {

    private final EmpCertService service;

    // 목록 
    @GetMapping
    public List<EmpCertVO> list(@RequestParam("empId") String empId) {
        EmpCertVO param = new EmpCertVO();
        param.setEmpId(empId);
        return service.selectEmpCertList(param);
    }

    // 등록 
    @PostMapping
    public Map<String, Object> create(@RequestBody EmpCertVO vo) {
        if (vo.getEmpId() == null || vo.getEmpId().isEmpty()) {
            return Map.of("success", false);
        }
        boolean ok = service.insertEmpCert(vo);
        return Map.of("success", ok);
    }

    // 삭제
    @DeleteMapping("/{certNo}")
    public Map<String, Object> delete(@PathVariable("certNo") Long certNo) {
        EmpCertVO vo = new EmpCertVO();
        vo.setCertNo(certNo);
        boolean ok = service.deleteEmpCert(vo);
        return Map.of("success", ok);
    }
}
