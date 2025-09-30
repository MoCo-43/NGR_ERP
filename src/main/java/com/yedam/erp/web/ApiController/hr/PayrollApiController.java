package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.PayrollService;
import com.yedam.erp.service.hr.DeptPayrollSumService;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;
import com.yedam.erp.vo.hr.DeptPayrollSumVO; // DB 저장용 VO
import com.yedam.erp.vo.hr.PayrollDeptSumVO; // 화면 합계용 DTO

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/payroll")
public class PayrollApiController {

	private final PayrollService service;
	private final DeptPayrollSumService deptPayrollSumService;

	// 급여대장 목록 조회
	@GetMapping
	public List<PayrollVO> list(@RequestParam(required = false) String yearMonth,
			@RequestParam(required = false) String deptCode) {
		Long companyCode = SessionUtil.companyId();
		PayrollVO vo = new PayrollVO();
		vo.setCompanyCode(companyCode);
		vo.setYearMonth(yearMonth);
		vo.setDeptCode(deptCode);
		return service.getPayrollListByCond(vo);
	}

	// 급여대장 단건 조회
	@GetMapping("/{payrollNo}")
	public PayrollVO detail(@PathVariable Long payrollNo) {
		return service.getPayroll(payrollNo);
	}

	// 급여대장 등록
	@PostMapping
	public Map<String, Object> create(@RequestBody PayrollVO vo) {
		vo.setCompanyCode(SessionUtil.companyId());
		if (vo.getStatus() == null || vo.getStatus().isBlank()) {
			vo.setStatus("DRAFT");
		}
		int ok = service.addPayroll(vo);
		return Map.of("success", ok > 0);
	}

	// 급여대장 수정
	@PutMapping("/{payrollNo}")
	public Map<String, Object> update(@PathVariable Long payrollNo, @RequestBody PayrollVO vo) {
		vo.setPayrollNo(payrollNo);
		vo.setCompanyCode(SessionUtil.companyId());
		int ok = service.editPayroll(vo);
		return Map.of("success", ok > 0);
	}

	// 급여대장 상태 변경 (확정/취소)
	@PatchMapping("/{payrollNo}/status")
	public Map<String, Object> changeStatus(@PathVariable Long payrollNo, @RequestBody PayrollVO vo) {
		vo.setPayrollNo(payrollNo);
		vo.setCompanyCode(SessionUtil.companyId());
		int ok = service.changePayrollStatus(vo);
		return Map.of("success", ok > 0);
	}

	// 사원별 급여내역 조회
	@GetMapping("/{payrollNo}/summary")
	public List<PayrollSummaryVO> summary(@PathVariable Long payrollNo) {
		return service.getPayrollSummary(payrollNo);
	}

	// 화면 하단 합계 조회 (조회 전용 DTO)
	@GetMapping("/{payrollNo}/dept-sum")
	public PayrollDeptSumVO getDeptSum(@PathVariable Long payrollNo) {
		return service.getDeptSum(payrollNo);
	}

	// 사원별 공제 저장
	@PostMapping("/{payrollNo}/deduct")
	public Map<String, Object> upsertDeduct(@PathVariable Long payrollNo, @RequestBody PayrollSummaryVO body) {
		body.setPayrollNo(payrollNo);
		int ok = service.upsertDeduct(body);
		return Map.of("success", ok > 0);
	}

	// 부서 확정 합계 저장 (DB 테이블 매핑 VO)
	@PostMapping("/{payrollNo}/dept-sum/save")
	public Map<String, Object> saveDeptSum(@PathVariable Long payrollNo,
			@RequestBody(required = false) DeptPayrollSumVO vo) {
		if (vo == null)
			vo = new DeptPayrollSumVO();

		// 연월/부서코드 보완
		PayrollVO payroll = service.getPayroll(payrollNo);
		if (payroll != null) {
			if (vo.getDeptCode() == null || vo.getDeptCode().isBlank()) {
				vo.setDeptCode(payroll.getDeptCode());
			}
			if (vo.getYearMonth() == null || vo.getYearMonth().isBlank()) {
				vo.setYearMonth(payroll.getYearMonth());
			}
		}

		vo.setCompanyCode(SessionUtil.companyId());

		// 존재 여부 확인 → 있으면 update, 없으면 insert
		List<DeptPayrollSumVO> existing = deptPayrollSumService.getDeptPayrollSumList(vo.getYearMonth(),
				vo.getCompanyCode());

		boolean exists = false;
		if (existing != null) {
			for (DeptPayrollSumVO item : existing) {
				if (Objects.equals(item.getDeptCode(), vo.getDeptCode())) {
					exists = true;
					break;
				}
			}
		}

		int ok = exists ? deptPayrollSumService.updateDeptPayrollSum(vo)
				: deptPayrollSumService.insertDeptPayrollSum(vo);

		return Map.of("success", ok > 0);
	}
}
