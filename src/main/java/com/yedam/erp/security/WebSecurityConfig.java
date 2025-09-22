package com.yedam.erp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // PasswordEncoder 빈을 최상위에 정의하여 순환 참조를 방지합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
					.requestMatchers("/**","/*.css", "/*.js","/img/**", "/prodimg/**", "/sign/**").permitAll()
					)
			.formLogin(form -> form
				      .loginPage("/login")
				      .loginProcessingUrl("/login")
				      .usernameParameter("empId")
				      .passwordParameter("empPw")
				      .defaultSuccessUrl("/", true)
				      .failureUrl("/login?error")
				      .permitAll()
				)
			  .logout(logout -> logout.permitAll());
			return http.build();
	}
}