package com.yedam.erp.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.hr.EmpVO;
import com.yedam.erp.vo.main.EmpLoginVO;

public interface EmpLoginMapper {

    // 사원 계정 조회
    EmpLoginVO findByEmpId(@Param("empId") String empId);

    // 사원 조회 (부서별)
    List<EmpVO> findEmployeesByDept(@Param("deptCode") String deptCode);

    // 신규 계정 생성
    void insertNewEmployeeLogin(EmpLoginVO empLoginVO);

    // 비밀번호 변경 및 mustChangePw 플래그 업데이트
    void updatePasswordAndFlag(
        @Param("empId") String empId,
        @Param("encodedPassword") String encodedPassword,
        @Param("mustChangePw") String mustChangePw
    );

    // 사원 상세 조회
    EmpVO findEmployeeById(@Param("empId") String empId);
    
    EmpLoginVO findByEmpIdAndComCode(@Param("empId") String empId, @Param("comCode") String comCode);
    int updatePasswordByEmpIdNo(@Param("empIdNo") Long empIdNo, @Param("password") String password);
}
