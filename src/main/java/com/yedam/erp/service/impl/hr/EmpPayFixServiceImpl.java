package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.EmpPayFixMapper;
import com.yedam.erp.service.hr.EmpPayFixService;
import com.yedam.erp.vo.hr.EmpPayFixVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpPayFixServiceImpl implements EmpPayFixService {

    private final EmpPayFixMapper empPayFixMapper;

    // 수당 목록 조회 
    @Override
    public List<EmpPayFixVO> getAllowList(String empId, Long companyCode) {
        return empPayFixMapper.selectAllowList(empId, companyCode);
    }

    //공제 목록 조회 
    @Override
    public List<EmpPayFixVO> getDeductList(String empId, Long companyCode) {
        return empPayFixMapper.selectDeductList(empId, companyCode);
    }

    //수당/공제 등록 
    @Override
    public int insertEmpPayFix(EmpPayFixVO vo) {
        return empPayFixMapper.insertEmpPayFix(vo);
    }

    // 사원 수당/공제 수정 
    @Override
    public int updateEmpPayFix(EmpPayFixVO vo) {
        return empPayFixMapper.updateEmpPayFix(vo);
    }
}
