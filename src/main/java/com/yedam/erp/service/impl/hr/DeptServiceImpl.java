package com.yedam.erp.service.impl.hr;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.hr.DeptMapper;
import com.yedam.erp.service.hr.DeptService;
import com.yedam.erp.vo.hr.DeptVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;
    //부서 조회
    @Override
    public List<DeptVO> getDeptList(Long companyCode) {
        return deptMapper.selectDeptList(companyCode);
    }
    //부서 단건 조회
    @Override
    public DeptVO getDept(String dept_code) {
        return deptMapper.selectDept(dept_code);
    }
    //부서 등록 
    @Override
    @Transactional
    public int addDept(DeptVO vo) {
        return deptMapper.insertDept(vo);
    }
    //부서 수정
    @Override
    @Transactional
    public int editDept(DeptVO vo) {
        return deptMapper.updateDept(vo);
    }

}
