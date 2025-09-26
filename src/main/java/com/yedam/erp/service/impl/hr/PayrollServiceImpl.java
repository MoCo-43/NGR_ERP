package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.PayrollMapper;
import com.yedam.erp.service.hr.PayrollService;
import com.yedam.erp.vo.hr.PayrollDeptSumVO;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollMapper payrollMapper;

    // 급여대장 마스터 

    @Override
    public List<PayrollVO> getPayrollList(Long companyCode) {
        return payrollMapper.selectPayrollList(companyCode);
    }

    @Override
    public PayrollVO getPayroll(Long payrollNo) {
        return payrollMapper.selectPayroll(payrollNo);
    }

    @Override
    public int addPayroll(PayrollVO vo) {
        return payrollMapper.insertPayroll(vo);
    }

    @Override
    public int editPayroll(PayrollVO vo) {
        return payrollMapper.updatePayroll(vo);
    }

    @Override
    public int changePayrollStatus(PayrollVO vo) {
        return payrollMapper.updatePayrollStatus(vo);
    }

    @Override
    public List<PayrollVO> getPayrollListByCond(PayrollVO vo) {
        return payrollMapper.selectPayrollListByCond(vo);
    }

    // 급여대장 상세 

    @Override
    public List<PayrollSummaryVO> getPayrollSummary(Long payrollNo) {
        return payrollMapper.selectPayrollSummary(payrollNo);
    }

    @Override
    public PayrollDeptSumVO getDeptSum(Long payrollNo) {
        return payrollMapper.selectDeptSum(payrollNo);
    }
}
