package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.account.AutoJournalRuleVO;

@Mapper
public interface AutoJournalMapper {
    List<AutoJournalRuleVO> selectRulesByPayType(Map<String, Object> param);
}