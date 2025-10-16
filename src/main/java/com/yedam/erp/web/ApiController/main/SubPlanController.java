package com.yedam.erp.web.ApiController.main;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yedam.erp.service.main.CompanyService;
import com.yedam.erp.service.main.SubPlanService;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.SubPlanVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubPlanController {

    private final SubPlanService subPlanService;
    private final CompanyService companyService;

    // 구독 계획 목록 반환
    @GetMapping("/api/subscribes")
    public List<SubPlanVO> subscribe() {
        return subPlanService.selectSubPlan(); // JSON 반환
    }

    // 회사 정보 + 구독 계획 함께 반환
    @GetMapping("/api/subDetail")
    public ResponseEntity<Map<String, Object>> subDetail(@RequestParam Long matNo) {
        CompanyVO company = companyService.getCompanyByMatNo(matNo);
        List<SubPlanVO> plans = subPlanService.selectSubPlan();

        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of(
            "company", company,
            "plans", plans
        ));
    }
    @GetMapping("/sal/salList")
    public ModelAndView allSubscriptionsView() {
        List<SubPlanVO> subscriptions = subPlanService.sallerList();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("main/salList");  // 보여줄 뷰 이름
        mav.addObject("subscriptions", subscriptions); // 뷰로 전달할 데이터

        return mav;
    }
}
