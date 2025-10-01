	package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.erp.vo.hr.EmpEduVO;

@Mapper
public interface EmpEduMapper {

    // 전체조회
    List<EmpEduVO> selectEmpEduList(@Param("empId") String empId);

    // 등록
    int insert(EmpEduVO vo);

    // 삭제
    int deleteByPk(@Param("eduNo") Long eduNo);
}
