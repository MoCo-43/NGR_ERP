//package com.yedam.erp.service.impl.main;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.yedam.erp.mapper.main.CompanyMapper;
//import com.yedam.erp.security.crypto.CryptoService;
//import com.yedam.erp.service.main.CompanyService;
//import com.yedam.erp.vo.main.CompanyVO;
//import com.yedam.erp.vo.main.EmpLoginVO;
//
//@Service
//public class CompanyServiceImpl implements CompanyService {
//
//    @Autowired
//    private CompanyMapper companyMapper;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
//    @Autowired
//    private CryptoService cryptoService;
//    
//    @Override
//    @Transactional
//    public void registerCompanyAndAdmin(CompanyVO company, EmpLoginVO admin) {
//        // AES 암호화 적용
//        company.setMatTel(cryptoService.encrypt(company.getMatTel()));
//        company.setMatMail(cryptoService.encrypt(company.getMatMail()));
//        company.setBizAccount(cryptoService.encrypt(company.getBizAccount()));
//        companyMapper.insertCompany(company);
//
//        // 관리자 비밀번호 암호화 (BCrypt)
//        String encodedPw = passwordEncoder.encode(admin.getEmpPw());
//        admin.setEmpPw(encodedPw);
//
//        admin.setMatNo(company.getMatNo());
//        companyMapper.insertCompanyAdmin(admin);
//    }
//
//    @Override
//    public CompanyVO getCompanyByComCode(String comCode) {
//       CompanyVO company = companyMapper.findByComCode(comCode).orElse(null);
//        if (company != null) {
//            // 복호화
//            company.setMatTel(cryptoService.decrypt(company.getMatTel()));
//            company.setMatMail(cryptoService.decrypt(company.getMatMail()));
//            company.setBizAccount(cryptoService.decrypt(company.getBizAccount()));
//        }
//        return company;
//    }
//
//    @Override
//    public CompanyVO getCompanyByMatNo(Long matNo) {
//        CompanyVO company = companyMapper.findByMatNo(matNo).orElse(null);
//        if (company != null) {
//            company.setMatTel(cryptoService.decrypt(company.getMatTel()));
//            company.setMatMail(cryptoService.decrypt(company.getMatMail()));
//            company.setBizAccount(cryptoService.decrypt(company.getBizAccount()));
//        }
//        return company;
//    }
//
//    @Override
//    public List<CompanyVO> getAllCompanies() {
//        List<CompanyVO> list = companyMapper.companyList();
//        for (CompanyVO company : list) {
//            company.setMatTel(cryptoService.decrypt(company.getMatTel()));
//            company.setMatMail(cryptoService.decrypt(company.getMatMail()));
//            company.setBizAccount(cryptoService.decrypt(company.getBizAccount()));
//        }
//        return list;
//    }
//}
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

	@Override
	public CompanyVO selectCompanyByNo(Long matNo) {
		return companyMapper.selectCompanyByNo(matNo);
	}

}