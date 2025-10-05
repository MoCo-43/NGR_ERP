package com.yedam.erp.web.ApiController.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedam.erp.service.main.SubPlanService;
import com.yedam.erp.vo.main.SubPlanVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubPageController {

    private final SubPlanService subPlanService;

    @GetMapping("/subDetails")
    public String getSubscriptionDetail(
            @RequestParam(name = "subPlanNo", required = false) Long subPlanNo,
            @RequestParam(name = "planId", required = false) Long planId,
            Model model) {

        Long effectiveId = subPlanNo != null ? subPlanNo : planId;
        if (effectiveId == null) {
            return "redirect:/subscribe"; // ID 없으면 목록으로
        }

        // SubPlanVO 조회
        SubPlanVO subPlan = subPlanService.selectSubPlanById(effectiveId);

        if (subPlan == null) {
            return "redirect:/subscribe"; // 없는 ID일 경우 목록으로
        }

        model.addAttribute("subPlan", subPlan); // 구독 플랜 상세 데이터 전달
        return "main/subDetail"; // templates/main/subDetail.html
    }
    
}
