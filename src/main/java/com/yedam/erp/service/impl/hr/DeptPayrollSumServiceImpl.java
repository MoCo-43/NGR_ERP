package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.DeptPayrollSumMapper;
import com.yedam.erp.service.hr.DeptPayrollSumService;
import com.yedam.erp.vo.hr.DeptPayrollSumVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptPayrollSumServiceImpl implements DeptPayrollSumService {

	private final DeptPayrollSumMapper mapper;

	@Override
	@Transactional
	public int insertDeptPayrollSum(DeptPayrollSumVO vo) {
		return mapper.insertDeptPayrollSum(vo);
	}

	@Override
	@Transactional
	public int updateDeptPayrollSum(DeptPayrollSumVO vo) {
		return mapper.updateDeptPayrollSum(vo);
	}

	@Override
	public DeptPayrollSumVO getDeptPayrollSum(Long confirmNo) {
		return mapper.selectDeptPayrollSum(confirmNo);
	}

	@Override
	public List<DeptPayrollSumVO> getDeptPayrollSumList(String yearMonth, Long companyCode) {
		return mapper.selectDeptPayrollSumList(yearMonth, companyCode);
	}
}
