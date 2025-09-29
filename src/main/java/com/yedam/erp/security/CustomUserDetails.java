package com.yedam.erp.security;

import com.yedam.erp.vo.main.EmpLoginVO;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Spring Security의 UserDetails 인터페이스를 구현한 사용자 정의 클래스입니다.
 * 이 클래스는 애플리케이션의 사용자 정보(EmpLoginVO)를 Spring Security가 이해할 수 있는 형태로 변환하는
 * 어댑터 역할을 수행합니다. 인증 및 인가 과정에서 Spring Security는 이 객체의 정보를 사용합니다.
 */
@Data
public class CustomUserDetails implements UserDetails {

    // 인증된 사용자의 핵심 정보를 담는 VO 객체
    private final EmpLoginVO empLoginVO;
    //private final String empName; // 이름 필드 추가
    /**
     * 생성자를 통해 DB 등에서 조회한 사용자 정보 객체(EmpLoginVO)를 주입받습니다.
     * @param empLoginVO 인증할 사용자의 정보
     */
    public CustomUserDetails(EmpLoginVO empLoginVO) {
        this.empLoginVO = empLoginVO;
        //this.empName = empLoginVO.getEmpNameFromJoin(); // join 해서 들고오기 
    }

    /**
     * UserDetails 인터페이스에 정의되지 않은 추가적인 사용자 정보(예: 이름, 부서 등)에 접근해야 할 경우를 위해
     * 원본 VO 객체를 반환하는 getter 메소드입니다.
     * @return EmpLoginVO 사용자 정보 원본 객체
     */
    public EmpLoginVO getEmpLoginVO() {
        return empLoginVO;
    }

    // UserDetails 인터페이스의 핵심 메소드 구현
    /**
     * 사용자가 가진 권한(Role) 목록을 반환합니다.
     * Spring Security는 이 정보를 기반으로 접근 제어(Authorization)를 수행합니다.
     * GrantedAuthority는 권한을 나타내는 인터페이스이며, 보통 'ROLE_ADMIN', 'ROLE_USER'와 같은 형태로 사용됩니다.
     *
     * [중요] 현재는 모든 사용자에게 'ROLE_USER'를 하드코딩하여 부여하고 있으나,
     * 실제 운영 환경에서는 DB에서 사용자의 역할(Role)을 조회하여 동적으로 설정해야 합니다.
     *
     * @return 사용자의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 예시: 단일 권한을 부여하는 경우
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * 사용자의 비밀번호를 반환
     * 이 값은 Spring Security의 AuthenticationProvider가 사용자가 제출한 비밀번호와 일치하는지 비교하는 데 사용
     * @return 암호화된 사용자 비밀번호
     */
    @Override
    public String getPassword() {
        return empLoginVO.getEmpPw();
    }

    /**
     * 사용자의 고유 식별자(ID)를 반환
     * Spring Security에서 'username'은 일반적으로 로그인 시 사용하는 ID를 의미하며, 중복되지 않는 값
     * @return 사용자 ID
     */
    @Override
    public String getUsername() {
        return empLoginVO.getEmpId();
    }


    // 계정의 상태를 관리하는 메소드들
    // 이 메소드들이 false를 반환하면, 해당 사유에 맞는 AuthenticationException이 발생하여 로그인이 거부됩니다
    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다.
     * @return true: 계정 만료되지 않음
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // 요구사항에 따라 DB의 계정 만료일 필드와 비교하는 로직으로 변경 가능
    }

    /**
     * 계정이 잠기지 않았는지 여부를 반환합니다.
     * 로그인 실패 횟수 초과 등의 사유로 계정이 잠겼는지 확인하는 데 사용됩니다.
     * @return true: 계정 잠기지 않음
     */
    @Override
    public boolean isAccountNonLocked() {
        // empLoginVO의 isLocked 필드가 'Y'가 아닐 때(잠기지 않았을 때) true를 반환합니다.
        return !"Y".equals(empLoginVO.getIsLocked());
    }

    /**
     * 비밀번호가 만료되지 않았는지 여부를 반환합니다.
     * @return true: 비밀번호 만료되지 않음
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 요구사항에 따라 DB의 비밀번호 만료일 필드와 비교하는 로직으로 변경 가능
    }

    /**
     * 계정이 활성화되어 있는지 여부를 반환합니다.
     * 탈퇴, 휴면 계정 등을 확인하는 데 사용됩니다.
     * @return true: 계정 활성화 상태
     */
    @Override
    public boolean isEnabled() {
        // empLoginVO의 isUsed 필드가 'Y'일 때(사용 중일 때) true를 반환합니다.
        return "Y".equals(empLoginVO.getIsUsed());
    }

	@Override
	public String toString() {
		return "CustomUserDetails [empLoginVO=" + empLoginVO + "]";
	}
    
    
}