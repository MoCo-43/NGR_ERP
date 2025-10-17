package com.yedam.erp.service.impl.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.erp.mapper.account.JournalCloseLogMapper;
import com.yedam.erp.service.account.JournalCloseLogService;
import com.yedam.erp.vo.account.JournalAmountSumVO;
import com.yedam.erp.vo.account.JournalCloseLogVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalCloseLogServiceImpl implements JournalCloseLogService {

    private final JournalCloseLogMapper logMapper;

    @Override
    @Transactional
    public void insertLog(Long companyCode, List<String> jrnNoList, String actionType, String loginUser, String remarks,String status) {
        // ✅ 합계 조회
    	   // ✅ 합계 조회
        JournalAmountSumVO sum = logMapper.sumAmountByJrnNos(companyCode, jrnNoList);

        JournalCloseLogVO vo = new JournalCloseLogVO();
        vo.setCompanyCode(companyCode);
        vo.setJrnRange(getRange(jrnNoList));
        vo.setActionType(actionType);
        vo.setDebitSum(sum != null ? sum.getDebit() : 0L);
        vo.setCreditSum(sum != null ? sum.getCredit() : 0L);
        vo.setCreatedBy(loginUser);
        vo.setRemarks(remarks);
        vo.setStatus(status);

        // ✅ 기존 insert → MERGE 로 변경
        logMapper.upsertJournalCloseLog(vo);
    }

    @Override
    public List<JournalCloseLogVO> getLogList(Long companyCode) {
        return logMapper.selectJournalCloseLogList(companyCode);
    }

    /**
     * 전표번호 범위 문자열 생성
     * ex) v-20250915-001, v-20250915-002, v-20250915-005 → "001~005"
     */
    private String getRange(List<String> jrnNoList) {
        if (jrnNoList == null || jrnNoList.isEmpty()) return "";

        List<Integer> suffixNums = jrnNoList.stream()
                .map(no -> {
                    try {
                        String suffix = no.substring(no.lastIndexOf("-") + 1);
                        return Integer.parseInt(suffix);
                    } catch (Exception e) {
                        return 0; // 파싱 실패 시 0
                    }
                })
                .sorted()
                .toList();

        int min = suffixNums.get(0);
        int max = suffixNums.get(suffixNums.size() - 1);

        return String.format("%03d~%03d", min, max);
    }

    @Override
    public List<JournalCloseLogVO> getLogByJrn(Long companyCode, String jrnNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyCode", companyCode);
        params.put("jrnNo", jrnNo);
        return logMapper.selectJournalCloseLogByJrn(params);
    }

}
