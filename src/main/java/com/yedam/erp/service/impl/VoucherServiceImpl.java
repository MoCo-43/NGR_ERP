package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.VoucherMapper;
import com.yedam.erp.service.VoucherService;
import com.yedam.erp.vo.VoucherVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherMapper mapper;

    @Override
    public List<VoucherVO> list(String type, String status, String keyword) {
        return mapper.selectList(type, status, keyword);
    }

    @Override
    public VoucherVO get(Long id) {
        return mapper.selectOne(id);
    }

    @Override
    @Transactional
    public Long create(VoucherVO vo) {
        mapper.insert(vo);        // ID는 IDENTITY 또는 시퀀스 트리거로 생성됨
        return vo.getId();
    }

    @Override
    @Transactional
    public void update(Long id, VoucherVO vo) {
        vo.setId(id);
        mapper.update(vo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        mapper.delete(id);
    }
}