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
		registry.addViewController("/register").setViewName("main/register");
		registry.addViewController("/subscribe").setViewName("main/subscribe");
		registry.addViewController("/subDetail").setViewName("main/subdetail");
		registry.addViewController("/eContract").setViewName("main/eContract");
		registry.addViewController("/findpw").setViewName("main/forgotpasword");
		// 영업

		registry.addViewController("/vouchers").setViewName("account/list");
		

		registry.addViewController("/biz/polist").setViewName("biz/ViewAllPO");
		registry.addViewController("/biz/poinsert").setViewName("biz/InsertPO");
		// 영업-주문이력조회
		registry.addViewController("/biz/pohistory").setViewName("biz/modals/PoHistoryModal");
		// 영업-품목코드조회
		registry.addViewController("/biz/productcode").setViewName("biz/modals/ProductCodeModal");

		// 회계
		registry.addViewController("/vouchers").setViewName("account/list");
		registry.addViewController("/accountList").setViewName("account/accountlist");
		registry.addViewController("/invoice").setViewName("account/invoice");
		registry.addViewController("/invoiceModal").setViewName("account/invoiceModal");
		registry.addViewController("/taxInvoice").setViewName("account/taxInvoice");
		registry.addViewController("/journal").setViewName("account/journal");
		registry.addViewController("/close").setViewName("account/close");
		
		// 재고
		registry.addViewController("/stock").setViewName("index");
		registry.addViewController("/stock/product/insert").setViewName("stock/insertProduct");
		registry.addViewController("/stock/plan/insert").setViewName("stock/insertOrderPlan");
		registry.addViewController("/stock/plan/list").setViewName("stock/orderPlanList");
		
		
		// 인사
		 registry.addViewController("/emps").setViewName("hr/EmpList");
		 registry.addViewController("/dept").setViewName("hr/deptList");

	}

}