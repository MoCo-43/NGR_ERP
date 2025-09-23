package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.DeptMapper;
import com.yedam.erp.service.DeptService;
import com.yedam.erp.vo.hr.DeptVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

	private final DeptMapper deptMapper;

	@Override
	public List<DeptVO> getDeptList(DeptVO param) {
		return deptMapper.selectDeptList(param);
	}

	@Override
	public DeptVO getDept(String dept_code) {
		return deptMapper.selectDept(dept_code);
	}

	@Override
	@Transactional
	public int addDept(DeptVO vo) {
		return deptMapper.insertDept(vo);
	}

	@Override
	@Transactional
	public int editDept(DeptVO vo) {
		return deptMapper.updateDept(vo);
	}

	@Override
	@Transactional
	public int removeDept(String dept_code) {
		return deptMapper.deleteDept(dept_code);
	}
}
