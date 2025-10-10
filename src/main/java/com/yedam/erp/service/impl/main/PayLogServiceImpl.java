package com.yedam.erp.service.impl.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.PayLogMapper;
import com.yedam.erp.service.main.PayLogService;
import com.yedam.erp.vo.main.PayLogVO;

@Service
public class PayLogServiceImpl implements PayLogService {

	@Autowired
	PayLogMapper mapper;
	@Override
	public List<PayLogVO> payLogList(String subCode) {
		return mapper.payLogList(subCode);
	}
 
}
