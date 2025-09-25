package com.yedam.erp.web.ApiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.hr.PayAllowCodeService;
import com.yedam.erp.vo.hr.PayAllowCodeVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/allow-codes")
public class PayAllowCodeApiController {

	private final PayAllowCodeService service;

	// ===== 목록 조회 (keyword/useYn은 VO로 바인딩) =====
	@GetMapping
	public List<PayAllowCodeVO> list(PayAllowCodeVO param) {
		return service.getList(param);
	}

	// ===== 단건 조회 =====
	@GetMapping("/{allowCode}")
	public PayAllowCodeVO detail(@PathVariable String allowCode) {
		return service.get(allowCode);
	}

	// ===== 등록 (form 또는 json) =====
	@PostMapping(consumes = { "application/x-www-form-urlencoded", "application/json" })
	public Map<String, Object> insert(@RequestBody(required = false) PayAllowCodeVO body, PayAllowCodeVO form) {
		PayAllowCodeVO vo = body != null ? body : form;
		boolean ok = service.insert(vo);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		if (!ok)
			res.put("message", "등록 실패(코드 중복 또는 입력값 확인)");
		return res;
	}

	// ===== 수정 (PK 기준) =====
	@PutMapping(path = "/{allowCode}", consumes = { "application/x-www-form-urlencoded", "application/json" })
	public Map<String, Object> update(@PathVariable String allowCode,
			@RequestBody(required = false) PayAllowCodeVO body, PayAllowCodeVO form) {
		PayAllowCodeVO vo = body != null ? body : form;
		vo.setAllowCode(allowCode);
		boolean ok = service.update(vo);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		if (!ok)
			res.put("message", "수정 실패(코드 확인)");
		return res;
	}

	// ===== 사용여부 변경 (삭제 대신) =====
	@PatchMapping("/{allowCode}/use-yn")
	public Map<String, Object> changeUseYn(@PathVariable String allowCode, @RequestParam String useYn) {
		boolean ok = service.changeUseYn(allowCode, useYn);
		Map<String, Object> res = new HashMap<>();
		res.put("success", ok);
		if (!ok)
			res.put("message", "사용여부 변경 실패(코드 혹은 값 확인: Y/N)");
		return res;
	}
}
