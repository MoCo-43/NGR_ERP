package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.hr.EmpPayFixVO;

@Mapper
public interface EmpPayFixMapper {

    // 단건 조회(미사용)
    EmpPayFixVO selectByEmpId(@Param("empId") String empId);

    // 코드표 + 보유값 Join 목록
    List<EmpPayFixVO> selectFixedItems(@Param("empId") String empId,
                                       @Param("companyCode") Long companyCode);

    // 단건 등록/수정/업서트
    int insert(EmpPayFixVO vo);
    int update(EmpPayFixVO vo);
    int upsert(EmpPayFixVO vo); // ← 오타 주의? 아래 XML과 일치해야 함
}
