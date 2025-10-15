package com.yedam.erp.vo.hr;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmpVO {
    private String emp_id;      // 사번
    private String name;        // 이름
    private String dept_code;   // 부서코드
    private String dept_name;   // 부서명
    private String position;    // 직급
    private String title;       // 직책
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;         // 생년월일
    private String phone;       // 전화번호
    private String email;       // 이메일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;      // 입사일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Integer familyCnt;     // 부양가족수 
    private String maritalStatus;  // 결혼여부
    private Integer childrenCnt;   // 자녀 수
    private Date leaveDate;     // 퇴사일자
    private String leaveReason; // 퇴사사유
    private String bankCode;    // 은행코드	
    private String bankName;    // 은행명
    private String accountNo;   // 계좌번호
    private String zipCode;     // 우편번호
    private String address;     // 주소
    private String accountName; //예금주
    private String addressDetail; //상세주소
    private Long companyCode; // 회사코드
}
