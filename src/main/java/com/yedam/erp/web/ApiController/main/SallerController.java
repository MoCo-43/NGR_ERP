package com.yedam.erp.web.ApiController.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.erp.service.main.SallerService;

@RestController
public class SallerController {

	@Autowired
	SallerService sallerService;
}
