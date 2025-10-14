package com.yedam.erp.web.ApiController.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.PlanService;
import com.yedam.erp.vo.main.PlanVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {  // 이름 오타 수정

    private final PlanService planService;

    // 전체 조회
    @GetMapping
    public List<PlanVO> getPlanList(PlanVO plan) {
        return planService.selectPlanList(plan);
    }

    // 단건 조회
    @GetMapping("/{planNo}")
    public ResponseEntity<PlanVO> getPlan(@PathVariable Long planNo) {
        PlanVO plan = planService.selectPlanById(planNo);
        return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }

    // 등록
    @PostMapping
    public ResponseEntity<PlanVO> createPlan(@RequestBody PlanVO plan) {
    	System.out.println("▶ POST /plan 호출됨 : " + plan);
        planService.insertPlan(plan);
        return new ResponseEntity<>(plan, HttpStatus.CREATED);
    }

    // 수정
    @PutMapping("/{planNo}")
    public ResponseEntity<PlanVO> updatePlan(@PathVariable Long planNo, @RequestBody PlanVO plan) {
        plan.setPlanNo(planNo);
        int result = planService.updatePlan(plan);
        return result > 0 ? ResponseEntity.ok(plan) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{planNo}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planNo, @RequestParam Long empIdNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("planNo", planNo);
        param.put("empIdNo", empIdNo);

        int result = planService.deletePlan(param);
        return result > 0 ? ResponseEntity.noContent().build() 
                          : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

