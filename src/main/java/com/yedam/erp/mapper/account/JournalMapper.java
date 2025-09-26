package com.yedam.erp.mapper.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.account.JournalVO;

@Mapper
public interface JournalMapper {
	String selectNextJrnNo();
    // 단건 등록
    int insertJournal(JournalVO vo);

    // 여러건 등록
    int insertJournalList(List<JournalVO> list);

    // 수정
    int updateJournal(JournalVO vo);

    // 삭제
    int deleteJournal(Long jrnNo);

    // 단건 조회
    JournalVO selectJournal(Long jrnNo);

    // 전체 조회
    List<JournalVO> selectJournalList(Long companyCode);
    
    // 마감용 전표 리스트
    List<JournalVO> selectJournalListClose(Long companyCode);

    // 마감용 전표 상세
    List<JournalVO> selectJournalDetailClose(Map<String, Object> params);

}
