package com.yedam.erp.security;

import org.springframework.security.core.userdetails.UserDetails;

//(다른 import 구문들은 그대로)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.yedam.erp.mapper.main.EmpLoginMapper;
import com.yedam.erp.vo.main.EmpLoginVO;
import jakarta.servlet.http.HttpServletRequest;

/**
* @description 로그인 폼에서 넘어온 정보를 검증하는 '인증 전문가'입니다.
*/
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

 @Autowired
 private EmpLoginMapper empLoginMapper; // DB 조회 전문가

 @Autowired
 @Lazy
 private PasswordEncoder passwordEncoder; // 비밀번호 암호화 전문가

 @Override
 public Authentication authenticate(Authentication authentication) throws AuthenticationException {
     
     // --- 1단계: 사용자가 입력한 로그인 정보 꺼내기 ---
     String empId = authentication.getName(); // 사용자가 적은 ID
     String empPw = (String) authentication.getCredentials(); // 사용자가 적은 비밀번호
     
     // [핵심] 로그인 폼의 '회사 ID'는 따로 챙겨줍니다.
     HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
     String matNoStr = request.getParameter("matNo");
     
     if (matNoStr == null || matNoStr.isEmpty()) {
         throw new BadCredentialsException("회사 아이디를 입력해주세요.");
     }
     Long matNo = Long.parseLong(matNoStr);

     
     // --- 2단계: DB에서 진짜 회원 정보 찾아오기 ---
     
     // 회사ID와 직원ID를 둘 다 사용해서 정확한 한 명을 찾아냅니다.
     EmpLoginVO userVO = empLoginMapper.findByEmpIdAndMatNo(empId, matNo);

     
     // --- 3단계: 정보가 맞는지 최종 확인 ---
     
     // 찾아온 회원이 없거나, 비밀번호가 다르면 로그인 실패!
     if (userVO == null || !passwordEncoder.matches(empPw, userVO.getEmpPw())) {
         throw new BadCredentialsException("아이디, 비밀번호 또는 회사 아이디가 틀렸습니다.");
     }
     
     
     // --- 4단계: 인증 성공! '표준 신분증' 발급해서 보고하기 ---
     
     // [수정 포인트 ①]
     // DB에서 찾은 '서류 뭉치(userVO)'를
     // 스프링 시큐리티가 알아보기 쉬운 '표준 신분증(CustomUserDetails)'에 담아줍니다.
     UserDetails userDetails = new CustomUserDetails(userVO);

     // [수정 포인트 ②]
     // "인증 성공했습니다!" 라고 보고할 때,
     // 지저분한 '서류 뭉치' 대신, 깔끔한 '표준 신분증'을 제출합니다.
     return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
 }

 @Override
 public boolean supports(Class<?> authentication) {
     return authentication.equals(UsernamePasswordAuthenticationToken.class);
 }
}















//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.yedam.erp.mapper.main.EmpLoginMapper;
//import com.yedam.erp.vo.main.EmpLoginVO;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Component // 스프링이 관리하는 Bean으로 등록
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private EmpLoginMapper empLoginMapper;
//
//    @Autowired
//    @Lazy 
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // 1. 사용자가 입력한 ID와 비밀번호를 가져온다.
//        String empId = authentication.getName();
//        String empPw = (String) authentication.getCredentials();
//
//        // 2. [핵심] Request 객체에서 matNo(회사아이디)를 직접 가져온다.
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String matNoStr = request.getParameter("matNo");
//        
//        if (matNoStr == null || matNoStr.trim().isEmpty()) {
//            throw new BadCredentialsException("회사 아이디를 입력해주세요.");
//        }
//        Long matNo = Long.parseLong(matNoStr);
//
//        // 3. DB에서 empId와 matNo로 사용자 정보를 조회한다. (1단계에서 만든 Mapper 메소드 사용)
//        EmpLoginVO user = empLoginMapper.findByEmpIdAndMatNo(empId, matNo);
//
//        // 4. 사용자 정보가 없거나 비밀번호가 일치하지 않으면 예외를 발생시킨다.
//        if (user == null || !passwordEncoder.matches(empPw, user.getEmpPw())) {
//            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못되었습니다.");
//        }
//
//        // 5. 인증 성공! 사용자 정보와 권한을 담아 Authentication 객체를 반환한다.
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 실제로는 DB에서 권한을 가져와야 합니다.
//        
//        return new UsernamePasswordAuthenticationToken(user, null, authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        // UsernamePasswordAuthenticationToken 타입의 인증 요청을 처리하겠다고 명시
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}