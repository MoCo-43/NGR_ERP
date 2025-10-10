package com.yedam.erp.web.ApiController.hr;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yedam.erp.service.hr.EmpPayFixService;
import com.yedam.erp.vo.hr.EmpPayFixVO;
import com.yedam.erp.security.SessionUtil;  // 👈 세션 유틸

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/emp-pay-fix")
public class EmpPayFixApiController {

    private final EmpPayFixService empPayFixService;

    // 활성 수당 목록 조회
    @GetMapping("/allow-list")
    public ResponseEntity<List<EmpPayFixVO>> getAllowList(
            @RequestParam(required = false) String empId
    ) {
        Long companyCode = SessionUtil.companyId();  // 👈 세션에서 회사코드 꺼냄
        List<EmpPayFixVO> list = empPayFixService.getAllowList(empId, companyCode);
        return ResponseEntity.ok(list);
    }

    // 사원 수당 등록
    @PostMapping
    public ResponseEntity<Integer> insertEmpPayFix(@RequestBody EmpPayFixVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());  // 👈 여기서도 세션값 주입
        int result = empPayFixService.insertEmpPayFix(vo);
        return ResponseEntity.ok(result);
    }

    // 사원 수당 수정
    @PutMapping
    public ResponseEntity<Integer> updateEmpPayFix(@RequestBody EmpPayFixVO vo) {
        vo.setCompanyCode(SessionUtil.companyId());  // 👈 세션값 주입
        int result = empPayFixService.updateEmpPayFix(vo);
        return ResponseEntity.ok(result);
    }
}
