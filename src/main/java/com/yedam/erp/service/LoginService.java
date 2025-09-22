// LoginService.java

package com.yedam.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.EmpLoginMapper;
import com.yedam.erp.vo.main.EmpLoginVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private EmpLoginMapper empLoginMapper;

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {

        EmpLoginVO user = empLoginMapper.findByEmpId(empId);
        
        if (user == null) {
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        
        // 사용자 역할(ROLE) 정보를 가져와 GrantedAuthority 리스트로 만듭니다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 예시: 모든 사용자에게 ROLE_USER 권한을 부여 (실제로는 DB에서 가져와야 함)
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new User(
            user.getEmpId(),            
            user.getEmpPw(),            
            authorities
        );
    }
}