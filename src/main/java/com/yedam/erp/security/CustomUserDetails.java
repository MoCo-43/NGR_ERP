package com.yedam.erp.security;


import com.yedam.erp.vo.main.EmpLoginVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final EmpLoginVO empLoginVO;

    public CustomUserDetails(EmpLoginVO empLoginVO) {
        this.empLoginVO = empLoginVO;
    }

    //  EmpLoginVO 객체를 외부에서 사용할 수 있도록 getter 추가
    public EmpLoginVO getEmpLoginVO() {
        return empLoginVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 일단 모든 사용자에게 "ROLE_USER" 권한 부여 (실제로는 DB 기반으로 해야 함)
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return empLoginVO.getEmpPw();
    }

    @Override
    public String getUsername() {
        // [핵심] 이제 스프링 시큐리티는 이 메소드를 통해 항상 순수한 empId를 얻게 됩니다.
        return empLoginVO.getEmpId();
    }

    // 아래는 계정 상태 관련 메소드들입니다.
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (항상 활성)
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"Y".equals(empLoginVO.getIsLocked()); // isLocked가 'Y'가 아니면 true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부 (항상 활성)
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(empLoginVO.getIsUsed()); // isUsed가 'Y'이면 true
    }
}