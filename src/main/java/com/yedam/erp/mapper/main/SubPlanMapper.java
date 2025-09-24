package com.yedam.erp.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.SubPlanVO;
@Mapper
public interface SubPlanMapper {
	List<SubPlanVO> selectSubPlan();
}
