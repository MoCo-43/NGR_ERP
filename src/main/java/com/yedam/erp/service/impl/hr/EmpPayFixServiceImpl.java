package com.yedam.erp.service.impl.hr;

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
}
