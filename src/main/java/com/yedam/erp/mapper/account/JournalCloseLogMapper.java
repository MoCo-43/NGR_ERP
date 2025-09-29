package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.account.JournalAmountSumVO;
import com.yedam.erp.vo.account.JournalCloseLogVO;

@Mapper
public interface JournalCloseLogMapper {

	int insertJournalCloseLog(JournalCloseLogVO vo);

	   JournalAmountSumVO sumAmountByJrnNos(
		        @Param("companyCode") Long companyCode,
		        @Param("jrnNos") List<String> jrnNos
		    );

    List<JournalCloseLogVO> selectJournalCloseLogList(Long companyCode);

    List<JournalCloseLogVO> selectJournalCloseLogByJrn(Map<String, Object> params);
}
