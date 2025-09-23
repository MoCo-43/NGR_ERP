package com.yedam.erp.mapper.hr;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yedam.erp.vo.hr.EmpPayFixVO;

@Mapper
public interface EmpPayFixMapper {

	EmpPayFixVO selectByEmpId(@Param("empId") String empId);

	int insert(EmpPayFixVO vo);

	int update(EmpPayFixVO vo);

	int upsert(EmpPayFixVO vo); // MERGE (선택)
}