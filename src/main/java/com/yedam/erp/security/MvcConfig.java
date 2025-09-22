package com.yedam.erp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		
		// 메인
		
		// 영업
		
		// 회계
		registry.addViewController("/vouchers").setViewName("account/list");
		registry.addViewController("/accountList").setViewName("account/accountlist");
		registry.addViewController("/invoice").setViewName("account/invoice");
		registry.addViewController("/invoiceModal").setViewName("account/invoiceModal");
		// 재고
		
		// 인사
		
		

	}

}