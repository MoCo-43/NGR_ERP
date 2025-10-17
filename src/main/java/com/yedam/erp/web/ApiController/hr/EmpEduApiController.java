package com.yedam.erp.web.ApiController.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.hr.EmpEduService;
import com.yedam.erp.vo.hr.EmpEduVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/edu")
public class EmpEduApiController {

	private final EmpEduService service;

	// 조회
	@GetMapping
	public List<EmpEduVO> list(@RequestParam("empId") String empId) {
		EmpEduVO param = new EmpEduVO();
		param.setEmpId(empId);
		return service.selectEmpEduList(param);
	}

	// 등록
	@PostMapping
	public Map<String, Object> create(@RequestBody EmpEduVO vo) {
	    Map<String, Object> res = new HashMap<>();
	    if (!StringUtils.hasText(vo.getEmpId())) {
	        res.put("success", false);
	        return res;
	    }
	    int cnt = service.insertEmpEdu(vo) ? 1 : 0;
	    res.put("success", cnt > 0);
	    return res;
	}

	// 삭제
	@DeleteMapping("/{eduNo}")
	public Map<String, Object> delete(@PathVariable("eduNo") Long eduNo) {
		EmpEduVO vo = new EmpEduVO();
		vo.setEduNo(eduNo);
		boolean ok = service.deleteEmpEdu(vo);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		return res;
	}
}
