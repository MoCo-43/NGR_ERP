package com.yedam.erp.web.ApiController;

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

    // ===== 조회: empId로 목록 =====
    @GetMapping
    public List<EmpEduVO> list(@RequestParam("empId") String empId) {
        EmpEduVO param = new EmpEduVO();
        param.setEmpId(empId);
        return service.selectEmpEduList(param);
    }

    // ===== 등록: JSON 바디 받기 =====
    @PostMapping   // <-- consumes 제거, @RequestBody 사용
    public Map<String, Object> create(@RequestBody EmpEduVO vo) {
        Map<String, Object> res = new HashMap<>();
        if (!StringUtils.hasText(vo.getEmpId())) {
            res.put("success", false);
            res.put("message", "사번(empId)이 필요합니다.");
            return res;
        }
        int cnt = service.insertEmpEdu(vo) ? 1 : 0; // 서비스 시그니처에 맞춰서
        res.put("success", cnt > 0);
        return res;
    }

    @DeleteMapping("/{eduNo}")
    public Map<String, Object> delete(@PathVariable("eduNo") Long eduNo) {
        EmpEduVO vo = new EmpEduVO();
        vo.setEduNo(eduNo); // VO에 eduNo 세팅
        boolean ok = service.deleteEmpEdu(vo);  // 기존 서비스 호출
        Map<String, Object> res = new HashMap<>();
        res.put("success", ok);
        return res;
    }
}
