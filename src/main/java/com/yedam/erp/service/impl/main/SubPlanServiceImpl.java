package com.yedam.erp.service.impl.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.SubPlanMapper;
import com.yedam.erp.service.main.SubPlanService;
import com.yedam.erp.vo.main.SubPlanVO;

@Service
public class SubPlanServiceImpl implements SubPlanService {
	@Autowired
	SubPlanMapper subPlanMapper;
	@Override
	public List<SubPlanVO> selectSubPlan() {
		return subPlanMapper.selectSubPlan() ;
	}

}
