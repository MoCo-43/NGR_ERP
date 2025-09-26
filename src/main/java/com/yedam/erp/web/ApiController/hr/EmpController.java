package com.yedam.erp.web.ApiController.hr;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.EmpService;
import com.yedam.erp.vo.hr.EmpVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emps")
public class EmpController {

	private final EmpService service;

	// 전체 조회
	@GetMapping
	public List<EmpVO> list() {
		return service.getEmpList(SessionUtil.companyId());
	}

	// 단건 조회
	@GetMapping("/{emp_id}")
	public EmpVO get(@org.springframework.web.bind.annotation.PathVariable("emp_id") String empId) {
		EmpVO param = new EmpVO();
		param.setEmp_id(empId);
		return service.getEmp(param);
	}

	@PostMapping
	public int create(@RequestBody EmpVO empVO) {
	    empVO.setCompanyCode(SessionUtil.companyId()); 
	    return service.insertEmp(empVO);
	}

	// 수정
	@PutMapping("/{emp_id}")
	public int update(@PathVariable("emp_id") String empId,
	                  @RequestBody EmpVO empVO) {
	    empVO.setEmp_id(empId);
	    empVO.setCompanyCode(SessionUtil.companyId()); 
	    return service.updateEmp(empVO);
	}

	// 팀장 조회
	@GetMapping("/managers")
	public List<EmpVO> managers() {
		return service.getManagers(SessionUtil.companyId());
	}
}