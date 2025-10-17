package com.yedam.erp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.erp.service.TestService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
public class MainController {

private final TestService testService;

  @GetMapping("/account/123")
  public String main(Model model){
    model.addAttribute("boardList", testService.selectAll());
    return "index";
  }

  @ResponseBody
  @GetMapping("/boards")
  public Object getBoards() {
    return testService.selectAll();
  }

  @GetMapping("/list")
  public String getList() {
	  return "index";
  }
  
 
}
