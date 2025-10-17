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
	//사원 조회
	@Override
	public List<EmpVO> getEmpList(Long companyCode) {
		return empMapper.selectEmpList(companyCode);
	}
	//사원 단건조회
	@Override
	public EmpVO getEmp(EmpVO empVO) {
		return empMapper.selectEmp(empVO);
	}
	//사원 등록
	@Override
	@Transactional
	public int insertEmp(EmpVO empVO) {
		return empMapper.insertEmp(empVO);
	}
	//사원 수정
	@Override
	@Transactional
	public int updateEmp(EmpVO empVO) {
		return empMapper.updateEmp(empVO);
	}
	//팀장 조회
	@Override
	public List<EmpVO> getManagers(Long companyCode) {
		return empMapper.selectManagers(companyCode);
	}
}
