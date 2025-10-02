package com.yedam.erp.mapper.main;

import org.apache.ibatis.annotations.Mapper;
import com.yedam.erp.vo.main.SubLogVO;

@Mapper
public interface SubLogMapper {
    void insertSubLog(SubLogVO vo);
}