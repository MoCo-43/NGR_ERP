package com.yedam.erp.service.main;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.SubPayVO;

@Mapper
public interface PayLogMapper {
    void insertPayLog(SubPayVO vo);
}
