package com.yedam.erp.security;

import org.springframework.security.core.userdetails.UserDetails;
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

/**w
 * CustomAuthenticationProvider
 * - 사용자 인증 처리 (회사ID + 직원ID + 비밀번호 검증)
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private EmpLoginMapper empLoginMapper;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 1. 요청 파라미터 추출
        String empId = authentication.getName();
        String empPw = (String) authentication.getCredentials();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        // 요청 파라미터 이름을 "comCode"로 가져와 변수명을 comCodeStr로 사용 (변경)
        String comCodeStr = request.getParameter("comCode"); 

        if (comCodeStr == null || comCodeStr.isEmpty()) {
            throw new BadCredentialsException("회사 아이디를 입력해주세요.");
        }
        
        // String.parseLong(matNoStr) 대신, String 타입 변수 comCode에 대입 (문법 오류 수정 및 변수명 변경)
        String comCode = comCodeStr; 

        // 2. DB 조회
        // Mapper 메서드 이름도 comCode에 맞게 변경해야 합니다. (findByEmpIdAndMatNo -> findByEmpIdAndComCode) (변경)
        EmpLoginVO userVO = empLoginMapper.findByEmpIdAndComCode(empId, comCode);

        // 3. 계정 검증
        if (userVO == null || !passwordEncoder.matches(empPw, userVO.getEmpPw())) {
            throw new BadCredentialsException("아이디, 비밀번호 또는 회사 아이디가 올바르지 않습니다.");
        }

        // 4. 인증 성공 처리
        UserDetails userDetails = new CustomUserDetails(userVO);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 어떤 타입이든 허용
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