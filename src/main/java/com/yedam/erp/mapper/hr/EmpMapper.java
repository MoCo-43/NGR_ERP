package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.EmpVO;

@Mapper
public interface EmpMapper {

    // 전체조회 
    List<EmpVO> selectEmpList(Long companyCode);

    // 단건 조회
    EmpVO selectEmp(EmpVO empVO);

    // 등록
    int insertEmp(EmpVO empVO);

    // 수정
    int updateEmp(EmpVO empVO);

    // 팀장 조회 
    List<EmpVO> selectManagers(Long companyCode);
}
