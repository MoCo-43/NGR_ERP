package com.yedam.erp.service.impl.main;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.main.PlanMapper;
import com.yedam.erp.service.main.PlanService;
import com.yedam.erp.vo.main.PlanVO;
@Transactional
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanMapper planMapper;

    // 등록
    @Override
    public int insertPlan(PlanVO plan) {
        LocalDateTime now = LocalDateTime.now();
        plan.setCreatedAt(now);
        plan.setUpdatedAt(now);
        
        int result = planMapper.insertPlan(plan);
        System.out.println("▶ insertPlan 실행됨, result=" + result + ", planNo=" + plan.getPlanNo());
        return result;
    }

    // 수정
    @Override
    public int updatePlan(PlanVO plan) {
        PlanVO dbPlan = planMapper.selectPlanById(plan.getPlanNo());
        if (dbPlan == null) return 0;

        // 본인 권한 체크
        if (!dbPlan.getEmpIdNo().equals(plan.getEmpIdNo())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        plan.setUpdatedAt(LocalDateTime.now());
        return planMapper.updatePlan(plan);
    }

    @Override
    public int deletePlan(Map<String, Object> param) {
        Long planNo = (Long) param.get("planNo");
        Long empIdNo = (Long) param.get("empIdNo");

        PlanVO dbPlan = planMapper.selectPlanById(planNo);
        if (dbPlan == null) return 0;

        if (!dbPlan.getEmpIdNo().equals(empIdNo)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        return planMapper.deletePlan(param);
    }
    // 전체 조회
    @Override
    public List<PlanVO> selectPlanList(PlanVO plan) {
        return planMapper.selectPlanList(plan);
    }

    // 단건 조회
    @Override
    public PlanVO selectPlanById(Long planNo) {
        return planMapper.selectPlanById(planNo);
    }
}
