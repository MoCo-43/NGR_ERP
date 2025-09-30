package com.yedam.erp.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.account.accountVO;

@Mapper
public interface accountMapper {
	
	// 계정 과목
	 List<accountVO> selectAll(Long comapanyCode);
	 int updateYN(String acctCode,Long companyCode);
	 int bulkInsert(List<accountVO> accounts);
	 

}
