package com.yedam.erp.service.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.EmpLoginMapper;
import com.yedam.erp.security.CustomUserDetails;
import com.yedam.erp.vo.main.EmpLoginVO;
//로그인하려는 사용자 정보를 DB에서 찾아오는 녀석
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmpLoginMapper empLoginMapper;

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {
        // DB에서 empId로 사용자 조회
        EmpLoginVO emp = empLoginMapper.findByEmpId(empId);
        if (emp == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + empId);
        }
        return new CustomUserDetails(emp);
    }
}	