package com.yedam.erp.web.ApiController.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//--- [수정] 필요한 클래스 임포트 ---
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.main.CompanyService;
import com.yedam.erp.vo.main.CompanyVO;
//------------------------------------

import com.yedam.erp.service.main.SubPlanService;
import com.yedam.erp.vo.main.SubPlanVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubPageController {

    private final SubPlanService subPlanService;
    private final CompanyService companyService; // CompanyService 의존성 주입

    @GetMapping("/subDetails")
    public String getSubscriptionDetail(
            @RequestParam(name = "planId", required = false) Long planId,
            Model model) {

        if (planId == null) {
            return "redirect:/subscribe"; // ID 없으면 목록으로
        }
        
        // 1. 기존 구독 플랜 정보 조회
        SubPlanVO subPlan = subPlanService.selectSubPlanById(planId);
        if (subPlan == null) {
            return "redirect:/subscribe"; // 없는 ID일 경우 목록으로
        }
        model.addAttribute("subPlan", subPlan);

        // --- [수정] 현재 로그인한 사용자의 회사 정보를 조회하여 Model에 추가 ---
        // SessionUtil을 사용하여 현재 로그인된 사용자의 고유 번호(matNo)를 가져옵니다.
        Long matNo = SessionUtil.companyId(); 
        
        // 가져온 matNo를 이용해 DB에서 회사 정보를 조회합니다.
        CompanyVO company = companyService.getCompanyByMatNo(matNo); 
        
        // 조회된 회사 정보를 "company"라는 이름으로 Model에 추가하여 HTML로 전달합니다.
        model.addAttribute("company", company); 
        // ----------------------------------------------------------------

        return "main/subDetail"; 
    }
}