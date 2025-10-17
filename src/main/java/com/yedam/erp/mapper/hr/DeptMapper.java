package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.DeptVO;
@Mapper
public interface DeptMapper {

    // 목록 
	List<DeptVO> selectDeptList(Long companyCode);

    // 단건
    DeptVO selectDept(String dept_code);

    // 등록
    int insertDept(DeptVO vo);
    //수정
    int updateDept(DeptVO vo);
}
