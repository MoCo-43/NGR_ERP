package com.yedam.erp.security;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {
	
	public static Long companyId() {
		SecurityContext context = SecurityContextHolder.getContext(); 
		// 인증 객체를 얻습니다. 
		Authentication authentication = context.getAuthentication(); 
		// 로그인한 사용자정보를 가진 객체를 얻습니다. 
		CustomUserDetails principal =(CustomUserDetails) authentication.getPrincipal(); 
		
		return principal.getEmpLoginVO().getMatNo();
	}
	
	public static String empId() {
		SecurityContext context = SecurityContextHolder.getContext(); 
		// 인증 객체를 얻습니다. 
		Authentication authentication = context.getAuthentication(); 
		// 로그인한 사용자정보를 가진 객체를 얻습니다. 
		
		CustomUserDetails principal =(CustomUserDetails) authentication.getPrincipal(); 
		
		return principal.getUsername();
	}
	
	public static String empName() {
		// 세션에 로그인된 사용자명 가져오기
		
		SecurityContext context = SecurityContextHolder.getContext(); 
		// 인증 객체를 얻습니다. 
		Authentication authentication = context.getAuthentication(); 
		// 로그인한 사용자정보를 가진 객체를 얻습니다. 
		
		CustomUserDetails principal =(CustomUserDetails) authentication.getPrincipal(); 
		
		return principal.getEmpLoginVO().getName();
	}

}
