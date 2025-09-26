package com.yedam.erp.service.account;

import java.util.List;

import com.yedam.erp.vo.account.JournalVO;

public interface JournalService {
	//다음 전표번호
	String getNextJrnNo();
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
}
