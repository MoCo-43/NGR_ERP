package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.EmpEduMapper;
import com.yedam.erp.service.EmpEduService;
import com.yedam.erp.vo.hr.EmpEduVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpEduServiceImpl implements EmpEduService {

    private final EmpEduMapper mapper;

    @Override
    public List<EmpEduVO> selectEmpEduList(EmpEduVO empEduVO) {
        return mapper.selectEmpEduList(empEduVO);
    }

    @Transactional
    @Override
    public boolean insertEmpEdu(EmpEduVO empEduVO) {
        // EDU_NO는 트리거로 자동 생성
        return mapper.insertEmpEdu(empEduVO) > 0;
    }

    @Transactional
    @Override
    public boolean deleteEmpEdu(EmpEduVO empEduVO) {
        return mapper.deleteEmpEdu(empEduVO) > 0;
    }
}

