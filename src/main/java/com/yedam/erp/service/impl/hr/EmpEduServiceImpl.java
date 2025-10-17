package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.EmpEduMapper;
import com.yedam.erp.service.hr.EmpEduService;
import com.yedam.erp.vo.hr.EmpEduVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpEduServiceImpl implements EmpEduService {

    private final EmpEduMapper empEduMapper;
    //교육 조회
    @Override
    public List<EmpEduVO> selectEmpEduList(EmpEduVO vo) {
        return empEduMapper.selectEmpEduList(vo != null ? vo.getEmpId() : null);
    }
    //교육 등록
    @Override
    public boolean insertEmpEdu(EmpEduVO vo) {
        return empEduMapper.insert(vo) == 1;
    }
    //교육 삭제
    @Override
    public boolean deleteEmpEdu(EmpEduVO vo) {
        return empEduMapper.deleteByPk(vo.getEduNo()) == 1;
    }
}
