package com.yedam.erp.mapper.main;

import com.yedam.erp.vo.main.EmpLoginVO;
import org.apache.ibatis.annotations.Param;

public interface EmpLoginMapper {

	EmpLoginVO findByEmpId(String empId);
    
    EmpLoginVO findByEmpIdAndMatNo(@Param("empId") String empId, @Param("matNo") String matNo);
}