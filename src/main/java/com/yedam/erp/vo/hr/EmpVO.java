package com.yedam.erp.vo.hr;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpVO {
	String emp_id; // 사번
	String name; // 이름
	String dept_code; // 부서코드
	String dept_name; // 부서명
	String position; // 직급
	String title; // 직책
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date birth; // 생년월일
	String phone; // 전화번호
	String email; // 이메일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date joinDate; // 입사일자
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date leaveDate; // 퇴사일자
	String leaveReason; // 퇴사사유
	String bankCode; // 은행코드
	String accountNo; // 계좌번호
	String zipCode; // 우편번호
	String address; // 주소
}
