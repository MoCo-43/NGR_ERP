package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.hr.PayrollService;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/payroll")
public class PayrollApiController {

    private final PayrollService payrollService;


    // 급여대장 목록

    @GetMapping("/list")
    public List<PayrollVO> getPayrollList(@RequestParam Long companyCode,
                                          @RequestParam(required = false) String yearMonth) {
        PayrollVO vo = new PayrollVO();
        vo.setCompanyCode(companyCode);
        vo.setYearMonth(yearMonth);
        return payrollService.selectPayrollList(vo);
    }


    // 급여대장 단건

    @GetMapping("/{payrollNo}")
    public PayrollVO getPayroll(@PathVariable Long payrollNo,
                                @RequestParam Long companyCode) {
        PayrollVO vo = new PayrollVO();
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode);
        return payrollService.selectPayroll(vo);
    }


    // 급여대장 등록

    @PostMapping
    public int insertPayroll(@RequestBody PayrollVO vo) {
        return payrollService.insertPayroll(vo);
    }

    // 급여대장 수정

    @PutMapping
    public int updatePayroll(@RequestBody PayrollVO vo) {
        return payrollService.updatePayroll(vo);
    }


    // 상태 변경 (확정/취소)

    @PutMapping("/status")
    public int updatePayrollStatus(@RequestBody PayrollVO vo) {
        return payrollService.updatePayrollStatus(vo);
    }


    // 급여 상세 (동적 PIVOT)

    @GetMapping("/{payrollNo}/detail")
    public List<Map<String, Object>> getPayrollDetail(@PathVariable Long payrollNo,
                                                      @RequestParam Long companyCode) {
        return payrollService.selectPayrollDetailPivot(payrollNo, companyCode);
    }

    // 부서 합계

    @GetMapping("/{payrollNo}/dept-sum")
    public Map<String, Object> getDeptSum(@PathVariable Long payrollNo,
                                          @RequestParam Long companyCode) {
        PayrollVO vo = new PayrollVO();
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode);
        return payrollService.selectDeptSum(vo);
    }

    // 공제 저장
    @PostMapping("/deduct")
    public int upsertDeduct(@RequestBody PayrollSummaryVO vo) {
        return payrollService.upsertDeduct(vo);
    }
}
