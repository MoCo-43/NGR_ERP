package com.yedam.erp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
					//모든거 다 여는거 /**
					.requestMatchers("/**","/*.css", "/*.js","/img/**", "/prodimg/**", "/sign/**").permitAll()
					);
		return http.build();
	}
	
}
