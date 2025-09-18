package com.yedam.erp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.BookVO;

@Mapper
public interface TestMapper {

  List<BookVO> selectAll();


}
