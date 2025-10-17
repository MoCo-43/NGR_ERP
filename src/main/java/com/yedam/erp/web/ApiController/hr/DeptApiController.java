package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.DeptService;
import com.yedam.erp.vo.hr.DeptVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/dept")
public class DeptApiController {

    private final DeptService service;

    // 목록
    @GetMapping
    public List<DeptVO> list() {
        Long companyCode = SessionUtil.companyId();
        return service.getDeptList(companyCode);
    }

    // 단건
    @GetMapping("/{dept_code}")
    public DeptVO detail(@PathVariable("dept_code") String deptCode) {
        return service.getDept(deptCode);
    }

    // 등록
    @PostMapping
    public Map<String, Object> create(@RequestBody DeptVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());  
        int ok = service.addDept(vo);
        return Map.of("success", ok > 0);
    }

    // 수정
    @PutMapping("/{dept_code}")
    public Map<String, Object> update(@PathVariable("dept_code") String deptCode,
                                      @RequestBody DeptVO vo) {
        vo.setDept_code(deptCode);
        vo.setCompanyCode(SessionUtil.companyId());  
        int ok = service.editDept(vo);
        return Map.of("success", ok > 0);
    }


}
