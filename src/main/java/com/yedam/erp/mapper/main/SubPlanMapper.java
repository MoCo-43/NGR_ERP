package com.yedam.erp.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.main.SubPlanVO;
@Mapper
public interface SubPlanMapper {
	List<SubPlanVO> selectSubPlan();
	
	//모듈조회
	SubPlanVO  selectSubPlanById(@Param("subPlanNo") Long subPlanNo);
	//전체 구독 리스트 조회
	List<SubPlanVO> sallerList();	
}
