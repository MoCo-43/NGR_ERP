package com.yedam.erp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		
		// 메인
		registry.addViewController("/").setViewName("main/homeMain");
		registry.addViewController("/login").setViewName("main/login");
		registry.addViewController("/register").setViewName("main/login");
		registry.addViewController("/subscribe").setViewName("main/subscribe");
		// 영업

		registry.addViewController("/vouchers").setViewName("account/list");
		

		registry.addViewController("/biz/polist").setViewName("biz/ViewAllPO");
		registry.addViewController("/biz/poinsert").setViewName("biz/InsertPO");

		// 회계
		registry.addViewController("/vouchers").setViewName("account/list");
		registry.addViewController("/accountList").setViewName("account/accountlist");
		registry.addViewController("/invoice").setViewName("account/invoice");
		registry.addViewController("/invoiceModal").setViewName("account/invoiceModal");

		// 재고
		registry.addViewController("/stock").setViewName("index");
		registry.addViewController("/stock/product/insert").setViewName("stock/insertProduct");
		registry.addViewController("/stock/plan/insert").setViewName("stock/insertOrderPlan");
		
		
		// 인사

		

		 registry.addViewController("/emps").setViewName("hr/EmpList");
		
		 
		 

		

	}

}