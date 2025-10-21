package com.yedam.erp.mapper.main;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yedam.erp.vo.main.SallerVO;

@Mapper
public interface SallerMapper {
	SallerVO findSallerById(@Param("salId") String salId);
	SallerVO selectSallerByNo(Long sallerNo);

}
