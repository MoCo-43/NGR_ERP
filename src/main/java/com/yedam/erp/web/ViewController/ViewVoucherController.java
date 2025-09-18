package com.yedam.erp.web.ViewController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedam.erp.service.VoucherService;
import com.yedam.erp.vo.VoucherVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class ViewVoucherController {

    private final VoucherService service;

    // ----------- View (Thymeleaf) -----------
    @GetMapping("/vouchers")
    public String vouchers(Model model){
      model.addAttribute("activeMenu","vouchers");
      model.addAttribute("pageTitle","전표 목록");
      return "vouchers/list";
    }
}