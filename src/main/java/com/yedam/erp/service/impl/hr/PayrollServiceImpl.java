package com.yedam.erp.service.impl.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.PayrollMapper;
import com.yedam.erp.service.hr.PayrollService;
import com.yedam.erp.vo.hr.PayrollSummaryVO;
import com.yedam.erp.vo.hr.PayrollVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    private final PayrollMapper payrollMapper;
    // 급여대장 목록 조회
    @Override
    public List<PayrollVO> selectPayrollList(PayrollVO vo) {
        return payrollMapper.selectPayrollList(vo);
    }
    //단건조회
    @Override
    public PayrollVO selectPayroll(PayrollVO vo) {
        return payrollMapper.selectPayroll(vo);
    }
    //등록
    @Override
    public int insertPayroll(PayrollVO vo) {
        return payrollMapper.insertPayroll(vo);
    }
    //수정
    @Override
    public int updatePayroll(PayrollVO vo) {
        return payrollMapper.updatePayroll(vo);
    }
    //상태값 수정
    @Override
    public int updatePayrollStatus(PayrollVO vo) {
        return payrollMapper.updatePayrollStatus(vo);
    }
    //급여대장 목록 조회
    @Override
    public List<PayrollVO> selectPayrollListByCond(PayrollVO vo) {
        return payrollMapper.selectPayrollListByCond(vo);
    }
    //수당 조회
    @Override
    public String selectAllowColList(PayrollVO vo) {
        return payrollMapper.selectAllowColList(vo);
    }
    //공제 조회
    @Override
    public String selectDeductColList(PayrollVO vo) {
        return payrollMapper.selectDeductColList(vo);
    }
    //급여대장 상세 내역 조회
    @Override
    public List<Map<String, Object>> selectPayrollDetailPivot(Long payrollNo, Long companyCode) {
        PayrollVO vo = new PayrollVO();
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode);

        //리스트 조회 
        String allowCols = payrollMapper.selectAllowColList(vo);
        String deductCols = payrollMapper.selectDeductColList(vo);

        if (allowCols == null || allowCols.isBlank()) {
            allowCols = "''__DUMMY__'' AS \"AL___DUMMY__\"";
        }
        if (deductCols == null || deductCols.isBlank()) {
            deductCols = "''__DUMMY__'' AS \"DC___DUMMY__\"";
        }
        // 파라미터 맵 구성
        Map<String, Object> param = new HashMap<>();
        param.put("payrollNo", payrollNo);
        param.put("companyCode", companyCode);
        param.put("allowCols", allowCols);
        param.put("deductCols", deductCols);
        //Mapper 호출 (동적 PIVOT)
        return payrollMapper.selectPayrollDetailPivot(param);
    }

    //급여항목
    @Override
    public int upsertDeduct(PayrollSummaryVO vo) {
        return payrollMapper.upsertDeduct(vo);
    }
}
