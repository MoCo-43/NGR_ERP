package com.yedam.erp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.common.DashBoardVO;

@Mapper
public interface DashBoardMapper {
	List<DashBoardVO> selectActiveMembersByDepartment();
	
	List<DashBoardVO> selectProfitDash(Long companyCode);
}
