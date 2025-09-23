// LoginService.java

package com.yedam.erp.service.main;

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
    private EmpLoginMapper empLoginMapper;//로그인 사용자 정보 조회 

    //로그인 처리 할때 불러오고,파라미터 empId는 로그인 폼에서 사용자가 입력한 ID,반환값은 UserDetails 객체여야한다.
    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {

        EmpLoginVO user = empLoginMapper.findByEmpId(empId);
        //사용자가 존재하지 않을 경우
        if (user == null) {
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        
        // 사용자 역할(ROLE) 정보를 가져와 GrantedAuthority 리스트로 만듭니다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 예시: 모든 사용자에게 ROLE_USER 권한을 부여 (실제로는 DB에서 가져와야 함)
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new User(
            user.getEmpId(), //로그인Id         
            user.getEmpPw(), //DB조회한 암호화 비밀번호,BCryptPasswordEncoder매칭되어야하는 점이 중요하다.           
            authorities
        );
    }
}