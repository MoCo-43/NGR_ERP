package com.yedam.erp.mapper.main;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.EmpLoginVO;

@Mapper
public interface CompanyMapper {

    // 회사 등록
    void insertCompany(CompanyVO company);

    // 관리자 계정 등록
    void insertCompanyAdmin(EmpLoginVO admin);
    //값 o or x 
    Optional<CompanyVO> findByComCode(String comCode);
    
    Optional<CompanyVO> findByMatNo(Long matNo);

}