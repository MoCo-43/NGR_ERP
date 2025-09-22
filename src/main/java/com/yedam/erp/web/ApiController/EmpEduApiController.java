package com.yedam.erp.web.ApiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.EmpEduService;
import com.yedam.erp.vo.hr.EmpEduVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/edu")
public class EmpEduApiController {

    private final EmpEduService service;

    // ===== 조회: empId로 목록 (프론트가 대문자 키 기대하므로 변환해서 내려줌)
    @GetMapping
    public List<Map<String, Object>> list(@RequestParam("empId") String empId) {
        EmpEduVO param = new EmpEduVO();
        param.setEmpId(empId);
        List<EmpEduVO> vos = service.selectEmpEduList(param);

        List<Map<String, Object>> out = new ArrayList<>();
        for (EmpEduVO v : vos) {
            Map<String, Object> m = new HashMap<>();
            m.put("EDU_NO", v.getEduNo());
            m.put("EMP_ID", v.getEmpId());
            m.put("EDU_NAME", v.getEduName());
            m.put("EDU_TYPE", v.getEduType());
            m.put("RESULT", v.getResult());
            m.put("SCORE", v.getScore());
            m.put("COMPLETE_AT", v.getCompleteAt());
            m.put("EXPIRES_AT",  v.getExpiresAt());
            m.put("REMARKS",     v.getRemarks());
            out.add(m);
        }
        return out;
    }

    // ===== 등록 (form-urlencoded)
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public Map<String, Object> create(EmpEduVO vo) {
        Map<String, Object> res = new HashMap<>();
        if (vo.getEmpId() == null || vo.getEmpId().isEmpty()) {
            res.put("success", false);
            res.put("message", "사번(empId)이 필요합니다.");
            return res;
        }
        boolean ok = service.insertEmpEdu(vo);
        res.put("success", ok);
        return res;
    }

    // ===== 삭제
    @DeleteMapping("/{eduNo}")
    public Map<String, Object> delete(@PathVariable("eduNo") Long eduNo) {
        EmpEduVO vo = new EmpEduVO();
        vo.setEduNo(eduNo);
        boolean ok = service.deleteEmpEdu(vo);
        Map<String, Object> res = new HashMap<>();
        res.put("success", ok);
        return res;
    }
}