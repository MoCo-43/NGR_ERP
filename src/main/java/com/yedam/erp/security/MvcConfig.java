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
		registry.addViewController("/biz/polist").setViewName("biz/ViewAllPO");
		registry.addViewController("/biz/poinsert").setViewName("biz/InsertPO");
		// 회계
		
		// 재고
		
		// 인사
		registry.addViewController("/vouchers").setViewName("account/list");
		

	}

}