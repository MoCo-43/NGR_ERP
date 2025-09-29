package com.yedam.erp.service.main;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yedam.erp.vo.main.SubPlanVO;

@Service
public interface SubPlanService {

	List<SubPlanVO> selectSubPlan();
	SubPlanVO  selectSubPlanById( Long subPlanNo);
}
