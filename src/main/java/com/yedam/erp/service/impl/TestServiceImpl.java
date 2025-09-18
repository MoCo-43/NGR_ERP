package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.erp.mapper.TestMapper;
import com.yedam.erp.service.TestService;
import com.yedam.erp.vo.BookVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService{

final TestMapper testMapper;

@Override
public List<BookVO> selectAll() {
  
  return testMapper.selectAll();
}
}
