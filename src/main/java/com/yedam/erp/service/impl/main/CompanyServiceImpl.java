package com.yedam.erp.service.impl.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.main.CompanyMapper;
import com.yedam.erp.service.main.CompanyService;
import com.yedam.erp.vo.main.CompanyVO;
import com.yedam.erp.vo.main.EmpLoginVO;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerCompanyAndAdmin(CompanyVO company, EmpLoginVO admin) {
        companyMapper.insertCompany(company);

        String encodedPw = passwordEncoder.encode(admin.getEmpPw());
        admin.setEmpPw(encodedPw);

        admin.setMatNo(company.getMatNo());
        companyMapper.insertCompanyAdmin(admin);
    }

    @Override
    public CompanyVO getCompanyByComCode(String comCode) {
        return companyMapper.findByComCode(comCode).orElse(null);
    }

    @Override
    public CompanyVO getCompanyByMatNo(Long matNo) {
        return companyMapper.findByMatNo(matNo).orElse(null);
    }

    @Override
    public List<CompanyVO> getAllCompanies() {
        return companyMapper.companyList();
    }

}
