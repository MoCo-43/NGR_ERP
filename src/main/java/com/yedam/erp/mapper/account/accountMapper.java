package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.account.accountVO;

@Mapper
public interface accountMapper {
	 List<accountVO> selectAll(String category);
	 int updateYN(String acctCode);
	 int bulkInsert(List<accountVO> accounts);
}
