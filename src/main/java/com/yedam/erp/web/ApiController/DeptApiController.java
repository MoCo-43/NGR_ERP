package com.yedam.erp.web.ApiController;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.DeptService;
import com.yedam.erp.vo.hr.DeptVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/dept")
public class DeptApiController {

    private final DeptService service;

    // ===== 부서 전체 목록 / 검색 조회 =====
    @GetMapping
    public List<DeptVO> list(DeptVO param) {
        return service.getDeptList(param);
    }

    // ===== 부서 단건 조회 =====
    @GetMapping("/{dept_code}")
    public DeptVO detail(@PathVariable("dept_code") String deptCode) {
        return service.getDept(deptCode);
    }

    // ===== 부서 등록 =====
    @PostMapping
    public Map<String, Object> create(@RequestBody DeptVO vo) {
        int ok = service.addDept(vo);
        return Map.of("success", ok > 0);
    }

    // ===== 부서 수정 =====
    @PutMapping("/{dept_code}")
    public Map<String, Object> update(@PathVariable("dept_code") String deptCode,
                                      @RequestBody DeptVO vo) {
        vo.setDept_code(deptCode);
        int ok = service.editDept(vo);
        return Map.of("success", ok > 0);
    }

    // ===== 부서 삭제 =====
    @DeleteMapping("/{dept_code}")
    public Map<String, Object> delete(@PathVariable("dept_code") String deptCode) {
        int ok = service.removeDept(deptCode);
        return Map.of("success", ok > 0);
    }
}
