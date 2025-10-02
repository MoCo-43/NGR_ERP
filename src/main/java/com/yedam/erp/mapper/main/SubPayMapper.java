package com.yedam.erp.mapper.main;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.SubPayVO;

import org.apache.ibatis.annotations.Mapper;
import com.yedam.erp.vo.main.SubPayVO;

@Mapper
public interface SubPayMapper {
    void insertPayLog(SubPayVO vo);
}
