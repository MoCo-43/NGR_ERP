package com.yedam.erp.service;

import java.util.List;

import com.yedam.erp.vo.VoucherVO;

public interface VoucherService {
    List<VoucherVO> list(String type, String status, String keyword);
    VoucherVO get(Long id);
    Long create(VoucherVO vo);
    void update(Long id, VoucherVO vo);
    void delete(Long id);
}