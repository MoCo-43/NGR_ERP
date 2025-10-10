package com.yedam.erp.web.ApiController.hr;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.EmpPayFixService;
import com.yedam.erp.vo.hr.EmpPayFixVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emps")
public class EmpPayFixApiController {

    private final EmpPayFixService service;

    // 고정 수당
    @GetMapping("/{empId}/pay-fix/items")
    public List<EmpPayFixVO> getFixedItems(@PathVariable String empId) {
        Long companyCode = SessionUtil.companyId();   // 숫자 PK (예: 1001)
        return service.getFixedItems(empId, companyCode);
    }

    // 고정 수당 저장
    @PutMapping("/{empId}/pay-fix/items")
    public boolean saveFixedItems(@PathVariable String empId,
                                  @RequestBody List<EmpPayFixVO> items) {
        Long companyCode = SessionUtil.companyId();
        return service.saveFixedItems(empId, companyCode, items);
    }

   
    @Deprecated
    @GetMapping("/{empId}/pay-fix")
    public EmpPayFixVO getOne(@PathVariable String empId) {
        return service.get(empId);
    }

    @Deprecated
    @PostMapping("/{empId}/pay-fix")
    public boolean create(@PathVariable String empId, @RequestBody EmpPayFixVO vo) {
        vo.setEmpId(empId);
        vo.setCompanyCode(SessionUtil.companyId());
        return service.insert(vo);
    }

    @Deprecated
    @PutMapping("/{empId}/pay-fix")
    public boolean update(@PathVariable String empId, @RequestBody EmpPayFixVO vo) {
        vo.setEmpId(empId);
        vo.setCompanyCode(SessionUtil.companyId());
        return service.update(vo);
    }

    @Deprecated
    @PutMapping("/{empId}/pay-fix:upsert")
    public boolean upsert(@PathVariable String empId, @RequestBody EmpPayFixVO vo) {
        vo.setEmpId(empId);
        vo.setCompanyCode(SessionUtil.companyId());
        return service.upsert(vo);
    }
}
