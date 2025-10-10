package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.EmpPayFixMapper;
import com.yedam.erp.service.hr.EmpPayFixService;
import com.yedam.erp.vo.hr.EmpPayFixVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpPayFixServiceImpl implements EmpPayFixService {

    private final EmpPayFixMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public EmpPayFixVO get(String empId) {
        return mapper.selectByEmpId(empId);
    }

    @Override
    public boolean insert(EmpPayFixVO vo) {
        return mapper.insert(vo) == 1;
    }

    @Override
    public boolean update(EmpPayFixVO vo) {
        return mapper.update(vo) == 1;
    }

    @Override
    public boolean upsert(EmpPayFixVO vo) {
        return mapper.upsert(vo) >= 1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpPayFixVO> getFixedItems(String empId, Long companyCode) {
        return mapper.selectFixedItems(empId, companyCode);
    }

    @Override
    public boolean saveFixedItems(String empId, Long companyCode, List<EmpPayFixVO> items) {
        if (items == null || items.isEmpty()) return true;
        int total = 0;
        for (EmpPayFixVO it : items) {
            if (it == null) continue;
            it.setEmpId(empId);
            it.setCompanyCode(companyCode);
            if (it.getEmpAllowPay() == null) it.setEmpAllowPay(0L);
            total += mapper.upsert(it);
        }
        return total >= 1;
    }
}
