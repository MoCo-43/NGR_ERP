package com.yedam.erp.service.impl.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.JournalMapper;
import com.yedam.erp.service.account.JournalService;
import com.yedam.erp.vo.account.JournalVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private final JournalMapper journalMapper;

    @Override
    @Transactional
    public int insertJournal(JournalVO vo) {
        // Mapper XML의 <selectKey>에서 FN_NEXT_JRN_NO 로 jrnCode 생성되는 구조라면
        // 여기선 그대로 insert만 호출하면 됨.
        return journalMapper.insertJournal(vo);
    }

    @Override
    @Transactional
    public int insertJournalList(List<JournalVO> list) {
        // foreach batch가 Mapper XML에 구현되어 있어야 함
        return journalMapper.insertJournalList(list);
    }

    @Override
    @Transactional
    public int updateJournal(JournalVO vo) {
        return journalMapper.updateJournal(vo);
    }

    @Override
    @Transactional
    public int deleteJournal(Long jrnNo) {
        return journalMapper.deleteJournal(jrnNo);
    }

    @Override
    @Transactional(readOnly = true)
    public JournalVO selectJournal(Long jrnNo) {
        return journalMapper.selectJournal(jrnNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalVO> selectJournalList() {
        return journalMapper.selectJournalList();
    }

    @Override
    public String getNextJrnNo() {
        return journalMapper.selectNextJrnNo();
    }
}