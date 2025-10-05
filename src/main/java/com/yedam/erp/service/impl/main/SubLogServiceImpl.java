package com.yedam.erp.service.impl.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.SubLogMapper;
import com.yedam.erp.service.main.SubLogService;
import com.yedam.erp.vo.main.SubLogVO;

@Service
public class SubLogServiceImpl implements SubLogService {

	@Autowired
	SubLogMapper mapper;


}
