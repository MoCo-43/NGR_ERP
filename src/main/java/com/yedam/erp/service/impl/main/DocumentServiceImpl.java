package com.yedam.erp.service.impl.main;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.main.DocumentsMapper;
import com.yedam.erp.service.DocumentService;
import com.yedam.erp.vo.main.DocumentsVO;
@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	DocumentsMapper mapper;
	
	@Override
	public int insertSign(DocumentsVO vo) {
		return mapper.insertSign(vo);
	}

}
