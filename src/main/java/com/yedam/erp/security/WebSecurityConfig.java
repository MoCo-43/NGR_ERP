package com.yedam.erp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.yedam.erp.service.main.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	// AuthenticationManagerBuilder를 설정하여 Provider를 사용하도록 합니다.
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();// BCrypt 방식으로 암호화
	}
	//구독결제후 
	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/", "/api/**", "/*.css", "/*.js", "/img/**", "/prodimg/**", "/sign/**")
						.permitAll()
		                // 관리자 전용 페이지
				        //.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/salLogin/**").denyAll()
						.anyRequest().authenticated()
			            // 일반 사용자 접근 가능
			            //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
						)
				.formLogin(form -> form.loginPage("/login") // GET 로그인 페이지 표시
						.loginProcessingUrl("/login")// POST 로그인 처리
						.usernameParameter("empId")// 로그인 폼 username 필드 이름
						.passwordParameter("empPw")// 로그인 폼 password 필드 이름
						.defaultSuccessUrl("/dashboard", true)// 로그인 성공 후 이동할 URL
						.failureUrl("/login?error").permitAll()// 로그인 페이지는 인증 없이 접근 가능
				)

				// 로그인 저장 -> 서버 재실행 로그인 유지
				.rememberMe(remember -> remember.tokenRepository(tokenRepository(dataSource))
						.tokenValiditySeconds(60 * 60 * 24 * 3) // 3일
						.key("uniqueAndSecretKey").userDetailsService(customUserDetailsService))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")
						.deleteCookies("JSESSIONID")
//              .deleteCookies("JSESSIONID", "remember-me")// 로그아웃 시 쿠키 삭제
				).headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()) // PDF 시큐리티 허가(모달로 띄우려면 무조건 필요)
				);
		return http.build();
	}
	//구독결제전 order 우선순위 지정
	@Bean
	@Order(1)
	public SecurityFilterChain salSecurityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {
	    http
	        .securityMatcher("/salLogin", "/salLogin/**","/salLogout","/register","/checkId/**")
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/checkId/**","/salLogin", "/salLogin/**","/subDetails**","/register","/img/**", "/css/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/salLogin")
	            .loginProcessingUrl("/salLogin")
	            .usernameParameter("empId")
	            .passwordParameter("empPw")
	            .defaultSuccessUrl("/", true)
	            // 로그인 실패 시 콘솔 출력
	            .failureHandler((request, response, exception) -> {
	                System.out.println("로그인 실패: " + exception.getMessage());
	                response.sendRedirect("/salLogin?error");
	            })
	            // 로그인 성공 시 콘솔 출력
	            .successHandler((request, response, authentication) -> {
	                System.out.println("로그인 성공: " + authentication.getName());
	                response.sendRedirect("/");
	            })
	            .permitAll()
	        )
	        .rememberMe(remember -> remember
	            .tokenRepository(tokenRepository(dataSource))
	            .tokenValiditySeconds(60 * 60 * 24 * 3)
	            .key("salLoginKey")
	            .userDetailsService(customUserDetailsService)
	        )
	        .logout(logout -> logout
	            .logoutUrl("/salLogout")
	            .logoutSuccessHandler((request, response, authentication) -> {
	                System.out.println("로그아웃 성공: " + (authentication != null ? authentication.getName() : "익명"));
	                response.sendRedirect("/salLogin?logout");
	            })
	            .deleteCookies("JSESSIONID")
	        )
	        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

	    return http.build();
	}


//    @Bean
//    public PersistentTokenRepository tokenRepository(DataSource dataSource) {
//        return new MyTokenRepository(dataSource); // 커스텀 Repository 사용
//    }
//				// 로그인 저장 -> 서버 재실행 로그인 유지
//				.rememberMe(remember -> remember.tokenRepository(tokenRepository(dataSource))
//						.tokenValiditySeconds(60 * 60 * 24 * 3) // 3일
//						.key("uniqueAndSecretKey").userDetailsService(customUserDetailsService))
//				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")
//						.deleteCookies("JSESSIONID")
////              .deleteCookies("JSESSIONID", "remember-me")// 로그아웃 시 쿠키 삭제
//				).headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()) // PDF 시큐리티 허가(모달로 띄우려면 무조건 필요)
//				);
//
//		return http.build();
//	}

	@Bean
	public PersistentTokenRepository tokenRepository(DataSource dataSource) {
		return new MyTokenRepository(dataSource); // 커스텀 Repository 사용
	}
}
