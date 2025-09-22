package com.yedam.erp.web.ApiController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.EmpService;
import com.yedam.erp.vo.hr.EmpVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emps")
public class EmpController {

    private final EmpService service;

    // ----------- 전체 조회 -----------
    // 파라미터가 여러 개 올 수 있으니까 EmpVO 그대로 받도록 했음
    @GetMapping
    public List<EmpVO> list(EmpVO empVO) {
        return service.getEmpList(empVO);
    }

    // ----------- 단건 조회 -----------
    // emp_id만 채워서 VO로 전달
    @GetMapping("/{emp_id}")
    public EmpVO get(@org.springframework.web.bind.annotation.PathVariable("emp_id") String empId) {
        EmpVO param = new EmpVO();
        param.setEmp_id(empId);
        return service.getEmp(param);
    }

    // ----------- 등록 -----------
    @PostMapping
    public int create(@RequestBody EmpVO empVO) {
        return service.insertEmp(empVO);
    }

    // ----------- 수정 -----------
    @PutMapping("/{emp_id}")
    public int update(@org.springframework.web.bind.annotation.PathVariable("emp_id") String empId,
                      @RequestBody EmpVO empVO) {
        empVO.setEmp_id(empId);
        return service.updateEmp(empVO);
    }
}