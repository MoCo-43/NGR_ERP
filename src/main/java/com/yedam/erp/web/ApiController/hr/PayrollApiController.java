package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.hr.PayrollService;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payrolls")
public class PayrollApiController {

    private final PayrollService payrollService;

    // 회사코드 반환
    private long companyCode() {
        return Long.parseLong(SessionUtil.companyId().toString());
    }

    // 급여대장 목록
    @GetMapping
    public List<PayrollVO> list(@RequestParam(required = false) String yearMonth) {
        PayrollVO vo = new PayrollVO();
        vo.setCompanyCode(companyCode());
        vo.setYearMonth(yearMonth);
        return payrollService.selectPayrollList(vo);
    }

    // 급여대장 단건
    @GetMapping("/{payrollNo}")
    public PayrollVO get(@PathVariable Long payrollNo) {
        PayrollVO vo = new PayrollVO();
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode());
        return payrollService.selectPayroll(vo);
    }

    // 급여대장 등록
    @PostMapping
    public int create(@RequestBody PayrollVO vo) {
        vo.setCompanyCode(companyCode());
        return payrollService.insertPayroll(vo);
    }

    // 급여대장 수정
    @PutMapping("/{payrollNo}")
    public int update(@PathVariable Long payrollNo, @RequestBody PayrollVO vo) {
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode());
        return payrollService.updatePayroll(vo);
    }

    // 상태 변경
    @PatchMapping("/{payrollNo}/status")
    public int updateStatus(@PathVariable Long payrollNo, @RequestBody PayrollVO vo) {
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode());
        return payrollService.updatePayrollStatus(vo);
    }

    // 급여 상세 (피벗)
    @GetMapping("/{payrollNo}/detail")
    public List<Map<String, Object>> detail(@PathVariable Long payrollNo) {
        return payrollService.selectPayrollDetailPivot(payrollNo, companyCode());
    }

    // 공제 저장
    @PostMapping("/deduct")
    public int upsertDeduct(@RequestBody PayrollSummaryVO vo) {
        vo.setCompanyCode(companyCode());
        return payrollService.upsertDeduct(vo);
    }
}
