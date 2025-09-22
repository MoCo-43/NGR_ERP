package com.yedam.erp.mapper.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.hr.EmpEduVO;

@Mapper
public interface EmpEduMapper {

	// 사번별 교육 리스트 조회
	List<EmpEduVO> selectEmpEduList(EmpEduVO empEduVO);

	// 등록
	int insertEmpEdu(EmpEduVO empEduVO);

	// 삭제
	int deleteEmpEdu(EmpEduVO empEduVO);
}
