package com.yedam.erp.web.ApiController.hr;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.PayDeductCodeService;
import com.yedam.erp.vo.hr.PayDeductCodeVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/deduct-codes")
public class PayDeductCodeApiController {

	private final PayDeductCodeService service;
	//전체조회
	@GetMapping
	public List<PayDeductCodeVO> list() {
		return service.getDeductList(SessionUtil.companyId());
	}
	//단건조회
	@GetMapping("/{deductCode}")
	public PayDeductCodeVO detail(@PathVariable String deductCode) {
		PayDeductCodeVO param = new PayDeductCodeVO();
		param.setDeductCode(deductCode);
		param.setCompanyCode(SessionUtil.companyId());
		return service.getDeduct(param);
	}
	//등록
	@PostMapping
	public int create(@RequestBody PayDeductCodeVO vo) {
		vo.setCompanyCode(SessionUtil.companyId());
		return service.addDeduct(vo);
	}
	//수정
	@PutMapping("/{deductCode}")
	public int update(@PathVariable String deductCode, @RequestBody PayDeductCodeVO vo) {
		vo.setDeductCode(deductCode);
		vo.setCompanyCode(SessionUtil.companyId());
		return service.editDeduct(vo);
	}
	//사용여부
	@PatchMapping("/{deductCode}/use-yn")
	public int changeUseYn(@PathVariable String deductCode, @RequestParam String useYn) {
		PayDeductCodeVO vo = new PayDeductCodeVO();
		vo.setDeductCode(deductCode);
		vo.setUseYn(useYn);
		vo.setCompanyCode(SessionUtil.companyId());
		return service.changeUseYn(vo);
	}
}