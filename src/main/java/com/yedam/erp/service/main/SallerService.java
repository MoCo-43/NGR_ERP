package com.yedam.erp.service.main;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.main.SallerVO;

public interface SallerService {
	SallerVO sallerList(@Param("salId") String salId);
    SallerVO selectSallerByNo(@Param("sallerNo") Long sallerNo);
}
