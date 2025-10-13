package com.yedam.erp.web.ApiController.hr;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.hr.EmpPayFixService;
import com.yedam.erp.vo.hr.EmpPayFixVO;
import com.yedam.erp.security.SessionUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emp-pay-fix")
public class EmpPayFixApiController {

    private final EmpPayFixService empPayFixService;

    //수당 목록 조회
    @GetMapping("/allow-list")
    public ResponseEntity<List<EmpPayFixVO>> getAllowList(
            @RequestParam(required = false) String empId
    ) {
        Long companyCode = SessionUtil.companyId();
        List<EmpPayFixVO> list = empPayFixService.getAllowList(empId, companyCode);
        return ResponseEntity.ok(list);
    }

    //공제 목록 조회
    @GetMapping("/deduct-list")
    public ResponseEntity<List<EmpPayFixVO>> getDeductList(
            @RequestParam(required = false) String empId
    ) {
        Long companyCode = SessionUtil.companyId();
        List<EmpPayFixVO> list = empPayFixService.getDeductList(empId, companyCode);
        return ResponseEntity.ok(list);
    }

    //  사원 수당/공제 등록
    @PostMapping
    public ResponseEntity<Integer> insertEmpPayFix(@RequestBody EmpPayFixVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());
        int result = empPayFixService.insertEmpPayFix(vo);
        return ResponseEntity.ok(result);
    }

    // 사원 수당/공제 수정
    @PutMapping
    public ResponseEntity<Integer> updateEmpPayFix(@RequestBody EmpPayFixVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());
        int result = empPayFixService.updateEmpPayFix(vo);
        return ResponseEntity.ok(result);
    }
}
