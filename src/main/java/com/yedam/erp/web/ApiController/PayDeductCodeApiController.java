package com.yedam.erp.web.ApiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.hr.PayDeductCodeService;
import com.yedam.erp.vo.hr.PayDeductCodeVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/deduct-code")
public class PayDeductCodeApiController {

	private final PayDeductCodeService service;

	// 목록 조회
	@GetMapping
	public List<PayDeductCodeVO> list(@RequestParam(value = "useYn", required = false) String useYn,
			@RequestParam(value = "deductName", required = false) String deductName) {
		return service.getList(useYn, deductName);
	}

	// 단건 조회

	@GetMapping("/{deductCode}")
	public PayDeductCodeVO detail(@PathVariable String deductCode) {
		return service.get(deductCode);
	}

	// 등록
	@PostMapping
	public Map<String, Object> create(@RequestBody PayDeductCodeVO vo) {
		boolean ok = service.create(vo);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		if (ok)
			res.put("data", service.get(vo.getDeductCode()));
		return res;
	}

	// 수정
	@PutMapping("/{deductCode}")
	public Map<String, Object> update(@PathVariable String deductCode, @RequestBody PayDeductCodeVO vo) {
		vo.setDeductCode(deductCode);
		boolean ok = service.update(vo);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		if (ok)
			res.put("data", service.get(deductCode));
		return res;
	}

	// 사용여부 변경

	@PatchMapping("/{deductCode}/use")
	public Map<String, Object> changeUse(@PathVariable String deductCode, @RequestParam String useYn) {
		boolean ok = service.changeUseYn(deductCode, useYn);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		if (ok)
			res.put("data", service.get(deductCode));
		return res;
	}
}
