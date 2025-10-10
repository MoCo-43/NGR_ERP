package com.yedam.erp.service.hr;

import java.util.List;
import com.yedam.erp.vo.hr.EmpPayFixVO;

public interface EmpPayFixService {

    // 단건 조회
    EmpPayFixVO get(String empId);

    // 단건 등록/수정/업서트
    boolean insert(EmpPayFixVO vo);
    boolean update(EmpPayFixVO vo);
    boolean upsert(EmpPayFixVO vo);

    // 코드표 기반 고정항목 목록(사번 보유값 Join)
    List<EmpPayFixVO> getFixedItems(String empId, Long companyCode);

    // 목록 저장(배열 업서트)
    boolean saveFixedItems(String empId, Long companyCode, List<EmpPayFixVO> items);
}
