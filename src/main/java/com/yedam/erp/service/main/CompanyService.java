package com.yedam.erp.service.main;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.main.CompanyMapper;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.EmpLoginVO;

@Service
public class CompanyService {

    @Autowired
    private CompanyMapper companyMapper;
    
    @Autowired //비밀번호 암호화
    private PasswordEncoder passwordEncoder; 

    // 회사 + 관리자 등록 트랜잭션
    @Transactional
    public void registerCompanyAndAdmin(CompanyVO company, EmpLoginVO admin) {
        // 1. 회사 등록
        companyMapper.insertCompany(company);

        // 2️-1 관리자 계정 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(admin.getEmpPw());
        admin.setEmpPw(encodedPw);
   
        // 2. 관리자 계정 등록 (회사 matNo 참조)
        admin.setMatNo(company.getMatNo());
        companyMapper.insertCompanyAdmin(admin);
    }
    // comCode로 단일 회사 조회
    public CompanyVO getCompanyByComCode(String comCode) {
        Optional<CompanyVO> companyOpt = companyMapper.findByComCode(comCode);
        return companyOpt.orElse(null);
    }
    
}

