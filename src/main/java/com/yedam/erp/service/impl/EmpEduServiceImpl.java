package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.EmpEduMapper;
import com.yedam.erp.service.EmpEduService;
import com.yedam.erp.vo.hr.EmpEduVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpEduServiceImpl implements EmpEduService {

    private final EmpEduMapper empEduMapper;

    @Override
    public List<EmpEduVO> selectEmpEduList(EmpEduVO vo) {
        // Mapper는 empId만 필요하므로 VO에서 꺼내서 전달
        return empEduMapper.selectEmpEduList(vo != null ? vo.getEmpId() : null);
    }

    @Override
    public boolean insertEmpEdu(EmpEduVO vo) {
        return empEduMapper.insert(vo) == 1;
    }

    @Override
    public boolean deleteEmpEdu(EmpEduVO vo) {
        return empEduMapper.deleteByPk(vo.getEduNo()) == 1;
    }
}
