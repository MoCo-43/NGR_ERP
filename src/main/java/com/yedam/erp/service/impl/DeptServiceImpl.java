package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.hr.DeptMapper;
import com.yedam.erp.service.DeptService;
import com.yedam.erp.vo.hr.DeptVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptMapper mapper;

    @Override
    public List<DeptVO> getDeptList() {
        return mapper.selectDeptList();
    }

    @Override
    public void addDept(DeptVO vo) {
        mapper.insertDept(vo);
    }

    @Override
    public void editDept(DeptVO vo) {
        mapper.updateDept(vo);
    }

    @Override
    public void removeDept(String deptCode) {
        mapper.deleteDept(deptCode);
    }
}
