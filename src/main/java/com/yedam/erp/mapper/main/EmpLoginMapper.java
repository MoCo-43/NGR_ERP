package com.yedam.erp.mapper.main;

import com.yedam.erp.vo.main.EmpLoginVO;
import org.apache.ibatis.annotations.Param;

public interface EmpLoginMapper {

	EmpLoginVO findByEmpId(String empId);
    
    EmpLoginVO findByEmpIdAndComCode(@Param("empId") String empId, @Param("comCode") String comCode);
    
    void updatePasswordByEmpIdNo(@Param("empIdNo") Long empIdNo, @Param("newPassword") String newPassword);
    
    EmpLoginVO findByIdentityAndComCode(@Param("identity") String identity, @Param("comCode") String comCode);

}