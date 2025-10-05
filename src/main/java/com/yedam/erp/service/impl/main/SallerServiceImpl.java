package com.yedam.erp.service.impl.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.SallerMapper;
import com.yedam.erp.service.main.SallerService;
import com.yedam.erp.vo.main.SallerVO;

@Service
public class SallerServiceImpl implements SallerService{
	@Autowired
	SallerMapper mapper;
	@Override
	public List<SallerVO> sallerList() {
		return mapper.sallerList();
	}

}
