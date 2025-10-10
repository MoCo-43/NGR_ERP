package com.yedam.erp.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.SallerVO;

@Mapper
public interface SallerMapper {
	List<SallerVO> sallerList();
}
