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
		registry.addViewController("/eContract").setViewName("main/eContract");
		registry.addViewController("/findpw").setViewName("main/forgotpasword");
		registry.addViewController("/subpay").setViewName("main/subpay");
		registry.addViewController("/dashboard").setViewName("main/dashboard");
		registry.addViewController("/subpay").setViewName("main/subpay");
		//registry.addViewController("/sub/admin/subList/{matNo}").setViewName("main/submanager");
		registry.addViewController("/admin/hrmanager").setViewName("main/hrmanager");
		registry.addViewController("/sal/salLogin").setViewName("main/salLogin");
//		registry.addViewController("/sal/salList").setViewName("main/salList");
		// 영업

		registry.addViewController("/vouchers").setViewName("account/list");
		

		registry.addViewController("/biz/polist").setViewName("biz/ViewAllPO");
		registry.addViewController("/biz/poinsert").setViewName("biz/InsertPO");
		// 영업-주문이력조회
		registry.addViewController("/biz/pohistory").setViewName("biz/modals/PoHistoryModal");
		// 영업-품목코드조회
		registry.addViewController("/biz/productcode").setViewName("biz/modals/ProductCodeModal");
		// 영업-거래처조회
		registry.addViewController("/biz/customercode").setViewName("biz/modals/PoCustomerModal");
		// 영업-출하지시서 조회
		registry.addViewController("/biz/dolist").setViewName("biz/ViewAllDO");
		// 영업-출하지시서 입력페이지
		registry.addViewController("/biz/doinsert").setViewName("biz/InsertDO");
		// 영업-거래처관리 페이지
		registry.addViewController("/biz/mngcus").setViewName("biz/ManagementCustomer");

		// 회계
		registry.addViewController("/vouchers").setViewName("account/list");
		registry.addViewController("/accountList").setViewName("account/accountlist");
		registry.addViewController("/invoice").setViewName("account/invoice");
		registry.addViewController("/invoiceModal").setViewName("account/invoiceModal");
		registry.addViewController("/taxInvoice").setViewName("account/taxInvoice");
		//registry.addViewController("/journal").setViewName("account/journal");
		registry.addViewController("/close").setViewName("account/close");
		registry.addViewController("/payment").setViewName("account/payment");
		registry.addViewController("/moneyInvoice").setViewName("account/moneyInvoice");
		registry.addViewController("/income").setViewName("account/income");
		registry.addViewController("/balanceSheet").setViewName("account/balanceSheet");
		
		
		// 재고
		registry.addViewController("/stock").setViewName("index");
		registry.addViewController("/stock/product/insert").setViewName("stock/insertProduct");
		registry.addViewController("/stock/plan/insert").setViewName("stock/insertOrderPlan");
		registry.addViewController("/stock/plan/list").setViewName("stock/orderPlanList");
		registry.addViewController("/stock/order/insert").setViewName("stock/insertOrder");
		registry.addViewController("/stock/order/list").setViewName("stock/orderList");
		registry.addViewController("/stock/invenclosing/insert").setViewName("stock/insertInvenClosing");
		registry.addViewController("/stock/inbound/list").setViewName("stock/inboundList");
		registry.addViewController("/stock/inbound/insert").setViewName("stock/insertInbound");
		registry.addViewController("/stock/outbound/list").setViewName("stock/outboundList");
		registry.addViewController("/stock/outbound/insert").setViewName("stock/insertOutbound");
		
		// 인사
		 registry.addViewController("/emps").setViewName("hr/EmpList");
		 registry.addViewController("/dept").setViewName("hr/deptList");
		 registry.addViewController("/allowcode").setViewName("hr/PayAllowCode");
		 registry.addViewController("/deductcode").setViewName("hr/PayDeductCode");
		 registry.addViewController("/payroll").setViewName("hr/payrollList");
	}

}