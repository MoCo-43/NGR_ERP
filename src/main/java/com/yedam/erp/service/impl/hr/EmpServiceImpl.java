package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.EmpMapper;
import com.yedam.erp.service.hr.EmpService;
import com.yedam.erp.vo.hr.EmpVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

	private final EmpMapper empMapper;

	@Override
	public List<EmpVO> getEmpList(EmpVO empVO) {
		return empMapper.selectEmpList(empVO);
	}

	@Override
	public EmpVO getEmp(EmpVO empVO) {
		return empMapper.selectEmp(empVO);
	}

	@Override
	@Transactional
	public int insertEmp(EmpVO empVO) {
		return empMapper.insertEmp(empVO);
	}

	@Override
	@Transactional
	public int updateEmp(EmpVO empVO) {
		return empMapper.updateEmp(empVO);
	}

	@Override
	public List<EmpVO> getManagers(EmpVO empVO) {
		return empMapper.selectManagers(empVO);
	}
}