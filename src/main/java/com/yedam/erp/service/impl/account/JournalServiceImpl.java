package com.yedam.erp.service.impl.account;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.JournalMapper;
import com.yedam.erp.security.SessionUtil;
import com.yedam.erp.service.account.JournalCloseLogService;
import com.yedam.erp.service.account.JournalService;
import com.yedam.erp.vo.account.JournalVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private final JournalMapper journalMapper;
    private final JournalCloseLogService logService;
    
    
    @Override
    @Transactional
    public int insertJournal(JournalVO vo) {
        // Mapper XML의 <selectKey>에서 FN_NEXT_JRN_NO 로 jrnCode 생성되는 구조라면
        // 여기선 그대로 insert만 호출하면 됨.
        return journalMapper.insertJournal(vo);
    }

    // 매출매입 전표 detailGrid 등록 (다중행)
    @Override
    @Transactional
    public int insertJournalList(List<JournalVO> list) {
    	int count = 0;
        for (JournalVO vo : list) {
          count += journalMapper.insertJournal(vo);
        }
        return count;
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
    public List<JournalVO> selectJournalList(Long companyCode) {
        return journalMapper.selectJournalList(companyCode);
    }

    @Override
    public String getNextJrnNo() {
        return journalMapper.selectNextJrnNo();
        
        
    }

    @Override
    public List<JournalVO> selectJournalListClose(Long companyCode) {
        return journalMapper.selectJournalListClose(companyCode);
    }

    
    @Override
    public List<JournalVO> selectJournalDetailClose(String jrnNo, Long companyCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("jrnNo", jrnNo);
        params.put("companyCode", companyCode);
        return journalMapper.selectJournalDetailClose(params);
    }
    
    
    // 마감 제출 버튼
    @Override
    @Transactional
    public int updateStatusBatch(List<String> jrnNoList, String status,String createdBy) {
        Map<String, Object> params = new HashMap<>();
        params.put("jrnNoList", jrnNoList);
        params.put("status", status);
        params.put("companyCode", SessionUtil.companyId());
        int result = journalMapper.updateStatusBatch(params);
       
        // 승인 로그 기록
            logService.insertLog(
                SessionUtil.companyId(),
                jrnNoList,
                "closed", // ✅ "approve" 또는 "closed" 그대로 저장
                createdBy,
                "전표 승인"
            );
        return result;
    }
    
    // 역분개 

    @Override
    @Transactional
    public void reverseJournalCsv(Long companyCode, String originJrnNos, String createdBy) {
        journalMapper.reverseJournalCsv(companyCode, originJrnNos, createdBy);
        List<String> jrnNoList = Arrays.stream(originJrnNos.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        logService.insertLog(
                companyCode,
                jrnNoList,
                "reverse",
                createdBy,
                "전표 역분개"
            );
        }
 }
    
