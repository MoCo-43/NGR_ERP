package com.yedam.erp.mapper.hr;

import java.util.List;
import com.yedam.erp.vo.hr.DeptVO;

public interface DeptMapper {

    // 목록 
    List<DeptVO> selectDeptList(DeptVO param);

    // 단건
    DeptVO selectDept(String dept_code);

    // 등록/수정/삭제
    int insertDept(DeptVO vo);
    int updateDept(DeptVO vo);
    int deleteDept(String dept_code);
}
