package com.yedam.erp.web.ApiController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedam.erp.service.main.CompanyService;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.EmpLoginVO;

@RestController
//@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper; // Spring이 빈으로 등록되어 있음

    @GetMapping("/register")
    public ModelAndView registerPage() {
        return new ModelAndView("main/register");
    }
    
    /**
     * 회사 + 관리자 등록 (Map 기반)
     */
    @PostMapping("/register")
    public String registerCompany(@RequestBody Map<String, Object> requestMap) {

        // Map → VO 변환
        CompanyVO company = objectMapper.convertValue(requestMap, CompanyVO.class);
        EmpLoginVO admin = objectMapper.convertValue(requestMap, EmpLoginVO.class);

        // Service 호출 (트랜잭션 처리)
        companyService.registerCompanyAndAdmin(company, admin);

        return "success";
    }
}