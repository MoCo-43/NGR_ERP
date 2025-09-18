package com.yedam.erp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.VoucherVO;

@Mapper
public interface VoucherMapper {
    List<VoucherVO> selectList(@Param("type") String type,
                               @Param("status") String status,
                               @Param("keyword") String keyword);

    VoucherVO selectOne(@Param("id") Long id);

    int insert(VoucherVO vo);
    int update(VoucherVO vo);
    int delete(@Param("id") Long id);
}
