package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.EmpCertMapper;
import com.yedam.erp.service.hr.EmpCertService;
import com.yedam.erp.vo.hr.EmpCertVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpCertServiceImpl implements EmpCertService {

	private final EmpCertMapper mapper;

	@Override
	public List<EmpCertVO> selectEmpCertList(EmpCertVO vo) {
		return mapper.selectEmpCertList(vo);
	}

	@Override
	public boolean insertEmpCert(EmpCertVO vo) {
		return mapper.insert(vo) == 1;
	}

	@Override
	public boolean deleteEmpCert(EmpCertVO vo) {
		return mapper.deleteByPk(vo.getCertNo()) == 1;
	}
}
