package com.yedam.erp.service.main;

import java.util.List;
import java.util.Map;

import com.yedam.erp.vo.main.PlanVO;

public interface PlanService {
    // 전체 조회 (옵션: empIdNo 기준 필터 가능)
    List<PlanVO> selectPlanList(PlanVO plan);

    // 단건 조회 (수정/삭제 권한 확인용)
    PlanVO selectPlanById(Long planNo);

    // 등록
    int insertPlan(PlanVO plan);

    // 수정 (empIdNo 기준 체크 포함)
    int updatePlan(PlanVO plan);

    // 삭제 (empIdNo 기준 체크 포함)
    int deletePlan(Map<String, Object> param);
    }
