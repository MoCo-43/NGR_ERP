package com.yedam.erp.web.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yedam.erp.security.SessionUtil;

@Controller
public class JournalController {

    @GetMapping("/journal")
    public String closePage(Model model) {
        model.addAttribute("loginId", SessionUtil.empId()); // 세션에서 로그인 사용자
        return "account/journal";  // Thymeleaf 템플릿
    }
}