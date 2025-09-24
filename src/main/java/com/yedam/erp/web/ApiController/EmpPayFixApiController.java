package com.yedam.erp.web.ApiController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.EmpPayFixService;
import com.yedam.erp.vo.hr.EmpPayFixVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emps")
public class EmpPayFixApiController {

    private final EmpPayFixService service;

    // 조회
    @GetMapping("/{emp_id}/pay-fix")
    public EmpPayFixVO get(@PathVariable("emp_id") String empId) {
        return service.get(empId);
    }

    // 등록 
    @PostMapping("/{emp_id}/pay-fix")
    public boolean create(@PathVariable("emp_id") String empId,
                          @RequestBody EmpPayFixVO vo) {
        vo.setEmp_id(empId);
        return service.insert(vo);
    }

    // 수정
    @PutMapping("/{emp_id}/pay-fix")
    public boolean update(@PathVariable("emp_id") String empId,
                          @RequestBody EmpPayFixVO vo) {
        vo.setEmp_id(empId);
        return service.update(vo);
    }

    // upsert
    @PutMapping("/{emp_id}/pay-fix:upsert")
    public boolean upsert(@PathVariable("emp_id") String empId,
                          @RequestBody EmpPayFixVO vo) {
        vo.setEmp_id(empId);
        return service.upsert(vo);
    }
}
