package com.yedam.erp.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.yedam.erp.vo.main.EmpLoginVO;
import com.yedam.erp.vo.main.SallerVO;

import lombok.Data;

/**
 * Spring Security의 UserDetails 인터페이스를 구현한 사용자 정의 클래스입니다.
 * 이 클래스는 직원(EmpLoginVO) 또는 판매자(SallerVO)의 정보를
 * Spring Security가 이해할 수 있는 형태로 변환합니다.
 */
@Data
public class CustomUserDetails implements UserDetails {

    // 직원 로그인용 VO
    private EmpLoginVO empLoginVO;

    // 판매자 로그인용 VO
    private SallerVO sallerVO;

    /** 직원 로그인용 생성자 */
    public CustomUserDetails(EmpLoginVO empLoginVO) {
        this.empLoginVO = empLoginVO;
    }

    /** 판매자 로그인용 생성자 */
    public CustomUserDetails(SallerVO sallerVO) {
        this.sallerVO = sallerVO;
    }

    /** 직원 정보 getter */
    public EmpLoginVO getEmpLoginVO() {
        return empLoginVO;
    }

    /** 판매자 정보 getter */
    public SallerVO getSallerVO() {
        return sallerVO;
    }

    /**
     * Spring Security가 접근 권한을 판단할 때 사용하는 메소드
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("=== [CustomUserDetails] getAuthorities() 호출됨 ===");

        // 1️⃣ 직원 로그인
        if (empLoginVO != null) {
            System.out.println("직원 로그인: empId = " + empLoginVO.getEmpId());
            String roleName = empLoginVO.getComName();

            // 권한명 비어 있으면 기본 ROLE_USER 부여
            if (!StringUtils.hasText(roleName)) {
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            }

            // ROLE_ 접두어 없으면 자동 부여
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            return Collections.singletonList(new SimpleGrantedAuthority(roleName));
        }

        // 2️⃣ 판매자 로그인
        if (sallerVO != null) {
            System.out.println("판매자 로그인: salId = " + sallerVO.getSalId());
            // 판매자는 고정적으로 ROLE_SELLER 권한 부여
            String roleName = "ROLE_SELLER";

            // 만약 회사명 기반으로 권한을 분기하고 싶을 때 (옵션)
            if (StringUtils.hasText(sallerVO.getComName())) {
                roleName = "ROLE_" + sallerVO.getComName().toUpperCase();
            }

            return Collections.singletonList(new SimpleGrantedAuthority(roleName));
        }

        // 3️⃣ 예외 (로그인정보 없음)
        System.out.println("경고: empLoginVO, sallerVO 모두 null입니다.");
        return Collections.emptyList();
    }

    /**
     * 비밀번호 반환
     */
    @Override
    public String getPassword() {
        if (empLoginVO != null) return empLoginVO.getEmpPw();
        if (sallerVO != null) return sallerVO.getSalPw();
        return null;
    }

    /**
     * 로그인 ID 반환
     */
    @Override
    public String getUsername() {
        if (empLoginVO != null) return empLoginVO.getEmpId();
        if (sallerVO != null) return sallerVO.getSalId();
        return null;
    }

    /**
     * 이름 반환 (직원 또는 판매자)
     */
    public String getEmpName() {
        if (empLoginVO != null && empLoginVO.getEmpVO() != null) {
            return empLoginVO.getEmpVO().getName();
        } else if (sallerVO != null) {
            return sallerVO.getSalName();
        }
        return "";
    }

    /**
     * 직원용: 계정 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 판매자 ID 반환
     */
    public String getSallerId() {
        return (sallerVO != null) ? sallerVO.getSalId() : null;
    }

    /**
     * 판매자 비밀번호 반환
     */
    public String getSallerPw() {
        return (sallerVO != null) ? sallerVO.getSalPw() : null;
    }

    /**
     * 계정 잠금 여부
     */
    public boolean isAccountNonLocked() {
        if (empLoginVO != null) {
            if ("Y".equals(empLoginVO.getIsLocked())) {
                Date lockUntil = empLoginVO.getLockUntil();
                if (lockUntil != null && lockUntil.before(new Date())) {
                    // 잠금 해제 시간 지남 → 자동 해제
                    return true;
                }
                return false;
            }
            return true;
        }
        return true; // 판매자 로그인은 잠금 로직 없음
    }

    /**
     * 비밀번호 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 사용 여부 (탈퇴, 비활성화 등)
     */
    @Override
    public boolean isEnabled() {
        if (empLoginVO != null) {
            return "Y".equals(empLoginVO.getIsUsed());
        }
        // 판매자는 기본 활성화
        return true;
    }

    @Override
    public String toString() {
        return "CustomUserDetails [empLoginVO=" + empLoginVO + ", sallerVO=" + sallerVO + "]";
    }
}

//package com.yedam.erp.security;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.util.StringUtils;
//
//import com.yedam.erp.vo.main.EmpLoginVO;
//import com.yedam.erp.vo.main.SallerVO;
//
//import lombok.Data;
//
///**
// * Spring Security의 UserDetails 인터페이스를 구현한 사용자 정의 클래스입니다.
// * 이 클래스는 애플리케이션의 사용자 정보(EmpLoginVO)를 Spring Security가 이해할 수 있는 형태로 변환하는
// * 어댑터 역할을 수행합니다. 인증 및 인가 과정에서 Spring Security는 이 객체의 정보를 사용합니다.
// */
//@Data
//public class CustomUserDetails implements UserDetails {
//
//    // 인증된 사용자의 핵심 정보를 담는 VO 객체
//    private EmpLoginVO empLoginVO;
//    private  SallerVO sallerVO;
//    //private final String empName; // 이름 필드 추가
//    /**
//     * 생성자를 통해 DB 등에서 조회한 사용자 정보 객체(EmpLoginVO)를 주입받습니다.
//     * @param empLoginVO 인증할 사용자의 정보
//     */
//    public CustomUserDetails(EmpLoginVO empLoginVO) {
//        this.empLoginVO = empLoginVO;
//        //this.empName = empLoginVO.getEmpNameFromJoin(); // join 해서 들고오기 
//    }
//    public CustomUserDetails(SallerVO sallerVO) {
//        this.sallerVO = sallerVO;
//    }
//    /**
//     * UserDetails 인터페이스에 정의되지 않은 추가적인 사용자 정보(예: 이름, 부서 등)에 접근해야 할 경우를 위해
//     * 원본 VO 객체를 반환하는 getter 메소드입니다.
//     * @return EmpLoginVO 사용자 정보 원본 객체
//     */
//    public EmpLoginVO getEmpLoginVO() {
//        return empLoginVO;
//    }
//    
//    public SallerVO getSallerVO() {
//    	return sallerVO;
//    }
//    // UserDetails 인터페이스의 핵심 메소드 구현
//    /**
//     * 사용자가 가진 권한(Role) 목록을 반환합니다.
//     * Spring Security는 이 정보를 기반으로 접근 제어(Authorization)를 수행합니다.
//     * GrantedAuthority는 권한을 나타내는 인터페이스이며, 보통 'ROLE_ADMIN', 'ROLE_USER'와 같은 형태로 사용됩니다.
//     *
//     * [중요] 현재는 모든 사용자에게 'ROLE_USER'를 하드코딩하여 부여하고 있으나,
//     * 실제 운영 환경에서는 DB에서 사용자의 역할(Role)을 조회하여 동적으로 설정해야 합니다.
//     *
//     * @return 사용자의 권한 목록
//     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
////        System.out.println("=== [CustomUserDetails] getAuthorities() 호출됨 ===");
////        System.out.println("empId: " + empLoginVO.getEmpId());
////        System.out.println("codeId: " + empLoginVO.getCodeId());
////        System.out.println("comName: " + empLoginVO.getComName());
//    	// 1. DB에 저장된 권한명
//    	String roleName = empLoginVO.getComName();
//    	System.out.println(roleName);
//    	//2.권한명이 비어있거나 공백일 경우
//    	if (!StringUtils.hasText(roleName)) {
//    		//권한이 없는사용자는 아무석도 못하게 하거나,기본권한을 준다.role_user
//    		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//    	}
//    	//3.정상적인 권한이 있을경우
//    	return Collections.singletonList(new SimpleGrantedAuthority(roleName));
//    	
//    	// 예시: 단일 권한을 부여하는 경우
//       // return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() {
////        // 1️ 공통권한테이블에서 join된 권한명 가져오기
////        String roleName = empLoginVO.getComName(); //  comName 사용
////
////        // 2️ ROLE_ 접두어가 없으면 자동으로 붙여주기
////        if (StringUtils.hasText(roleName) && !roleName.startsWith("ROLE_")) {
////            roleName = "ROLE_" + roleName;
////        }
////
////        // 3️ 권한이 비어있을 경우 기본 ROLE_USER 부여
////        if (!StringUtils.hasText(roleName)) {
////            roleName = "ROLE_USER";
////        }
////
////        // 4️ 확인용 로그
////        System.out.println("로그인된 권한명(comName): " + roleName);
////
////        // 5️ Spring Security 권한 객체로 반환
////        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
////    }
//
//    /**
//     * 사용자의 비밀번호를 반환
//     * 이 값은 Spring Security의 AuthenticationProvider가 사용자가 제출한 비밀번호와 일치하는지 비교하는 데 사용
//     * @return 암호화된 사용자 비밀번호
//     */
//    @Override
//    public String getPassword() {
//        return empLoginVO.getEmpPw();
//    }
//
//    /**
//     * 사용자의 고유 식별자(ID)를 반환
//     * Spring Security에서 'username'은 일반적으로 로그인 시 사용하는 ID를 의미하며, 중복되지 않는 값
//     * @return 사용자 ID
//     */
//    @Override
//    public String getUsername() {
//        return empLoginVO.getEmpId();
//    }
//
//    public String getEmpName() {
//        if(empLoginVO != null && empLoginVO.getEmpVO() != null) {
//            return empLoginVO.getEmpVO().getName();
//        }
//        return ""; // 로그인 안 된 경우도 안전하게 처리
//    }
//    // 계정의 상태를 관리하는 메소드들
//    // 이 메소드들이 false를 반환하면, 해당 사유에 맞는 AuthenticationException이 발생하여 로그인이 거부됩니다
//    /**
//     * 계정이 만료되지 않았는지 여부를 반환합니다.
//     * @return true: 계정 만료되지 않음
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // 요구사항에 따라 DB의 계정 만료일 필드와 비교하는 로직으로 변경 가능
//    }
//
//    public String getSallerId() {
//    	return sallerVO.getSalId();
//    }
//    
//    public String getSallerPw() {
//    	return sallerVO.getSalPw();
//    }
//    /**
//     * 계정이 잠기지 않았는지 여부를 반환합니다.
//     * 로그인 실패 횟수 초과 등의 사유로 계정이 잠겼는지 확인하는 데 사용됩니다.
//     * @return true: 계정 잠기지 않음
//     */
////    @Override
////    public boolean isAccountNonLocked() {
////        // empLoginVO의 isLocked 필드가 'Y'가 아닐 때(잠기지 않았을 때) true를 반환합니다.
////        return !"Y".equals(empLoginVO.getIsLocked());
////    }
//
//    /**
//     * 비밀번호가 만료되지 않았는지 여부를 반환합니다.
//     * @return true: 비밀번호 만료되지 않음
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // 요구사항에 따라 DB의 비밀번호 만료일 필드와 비교하는 로직으로 변경 가능
//    }
//
//    /**
//     * 계정이 활성화되어 있는지 여부를 반환합니다.
//     * 탈퇴, 휴면 계정 등을 확인하는 데 사용됩니다.
//     * @return true: 계정 활성화 상태
//     */
//    @Override
//    public boolean isEnabled() {
//        // empLoginVO의 isUsed 필드가 'Y'일 때(사용 중일 때) true를 반환합니다.
//        return "Y".equals(empLoginVO.getIsUsed());
//    }
//
//	@Override
//	public String toString() {
//		return "CustomUserDetails [empLoginVO=" + empLoginVO + "]";
//	}
//    public boolean isAccountNonLocked() {
//        if ("Y".equals(empLoginVO.getIsLocked())) {
//            Date lockUntil = empLoginVO.getLockUntil();
//            if (lockUntil != null && lockUntil.before(new Date())) {
//                // 잠금 해제 시간 지남 → 자동 해제
//                return true;
//            }
//            return false;
//        }
//        return true;
//    } 
//    
//}