package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;

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
import com.yedam.erp.service.hr.PayrollService;
import com.yedam.erp.vo.hr.PayrollDeptSumVO;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/payroll")
public class PayrollApiController {

    private final PayrollService service;

    // 목록 (연월/부서 조건 + 회사코드 자동 주입)
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

    // 단건 (PK)
    @GetMapping("/{payrollNo}")
    public PayrollVO detail(@PathVariable("payrollNo") Long payrollNo) {
        return service.getPayroll(payrollNo);
    }

    // 등록
    @PostMapping
    public Map<String, Object> create(@RequestBody PayrollVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());
        // 기본값 가드 (원하면 제거 가능)
        if (vo.getStatus() == null || vo.getStatus().isBlank()) {
            vo.setStatus("DRAFT");
        }
        int ok = service.addPayroll(vo);
        return Map.of("success", ok > 0);
    }

    // 수정
    @PutMapping("/{payrollNo}")
    public Map<String, Object> update(@PathVariable("payrollNo") Long payrollNo,
                                      @RequestBody PayrollVO vo) {
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(SessionUtil.companyId());
        int ok = service.editPayroll(vo);
        return Map.of("success", ok > 0);
    }

    // 상태 변경 (확정/취소)
    @PatchMapping("/{payrollNo}/status")
    public Map<String, Object> changeStatus(@PathVariable("payrollNo") Long payrollNo,
                                            @RequestBody PayrollVO vo) {
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(SessionUtil.companyId());
        int ok = service.changePayrollStatus(vo); // ← 메서드명 수정
        return Map.of("success", ok > 0);
    }

    // 상세: 사원별 리스트
    @GetMapping("/{payrollNo}/summary")
    public List<PayrollSummaryVO> summary(@PathVariable("payrollNo") Long payrollNo) {
        return service.getPayrollSummary(payrollNo);
    }

    // 상세: 부서 합계
    @GetMapping("/{payrollNo}/dept-sum")
    public PayrollDeptSumVO deptSum(@PathVariable("payrollNo") Long payrollNo) {
        return service.getDeptSum(payrollNo);
    }
}
