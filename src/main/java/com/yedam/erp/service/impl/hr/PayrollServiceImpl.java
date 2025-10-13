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

    @Override
    public List<PayrollVO> selectPayrollList(PayrollVO vo) {
        return payrollMapper.selectPayrollList(vo);
    }

    @Override
    public PayrollVO selectPayroll(PayrollVO vo) {
        return payrollMapper.selectPayroll(vo);
    }

    @Override
    public int insertPayroll(PayrollVO vo) {
        return payrollMapper.insertPayroll(vo);
    }

    @Override
    public int updatePayroll(PayrollVO vo) {
        return payrollMapper.updatePayroll(vo);
    }

    @Override
    public int updatePayrollStatus(PayrollVO vo) {
        return payrollMapper.updatePayrollStatus(vo);
    }

    @Override
    public List<PayrollVO> selectPayrollListByCond(PayrollVO vo) {
        return payrollMapper.selectPayrollListByCond(vo);
    }

    @Override
    public String selectAllowColList(PayrollVO vo) {
        return payrollMapper.selectAllowColList(vo);
    }

    @Override
    public String selectDeductColList(PayrollVO vo) {
        return payrollMapper.selectDeductColList(vo);
    }

    @Override
    public List<Map<String, Object>> selectPayrollDetailPivot(Long payrollNo, Long companyCode) {
        // 1) 조회용 VO 생성
        PayrollVO vo = new PayrollVO();
        vo.setPayrollNo(payrollNo);
        vo.setCompanyCode(companyCode);

        // 2) 동적 PIVOT IN 리스트 조회 (LISTAGG 결과)
        //    XML에서 NVL로 기본값을 주지만, 혹시 모를 null/blank 방어 추가
        String allowCols = payrollMapper.selectAllowColList(vo);
        String deductCols = payrollMapper.selectDeductColList(vo);

        if (allowCols == null || allowCols.isBlank()) {
            // 오라클 PIVOT IN 에 최소 1개는 필요
            allowCols = "''__DUMMY__'' AS \"AL___DUMMY__\"";
        }
        if (deductCols == null || deductCols.isBlank()) {
            deductCols = "''__DUMMY__'' AS \"DC___DUMMY__\"";
        }

        // 3) 파라미터 맵 구성
        Map<String, Object> param = new HashMap<>();
        param.put("payrollNo", payrollNo);
        param.put("companyCode", companyCode);
        param.put("allowCols", allowCols);
        param.put("deductCols", deductCols);

        // 4) Mapper 호출 (동적 PIVOT)
        return payrollMapper.selectPayrollDetailPivot(param);
    }

    @Override
    public Map<String, Object> selectDeptSum(PayrollVO vo) {
        return payrollMapper.selectDeptSum(vo);
    }

    @Override
    public int upsertDeduct(PayrollSummaryVO vo) {
        return payrollMapper.upsertDeduct(vo);
    }
}
