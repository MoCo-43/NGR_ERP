package com.yedam.erp.mapper.hr;

import java.util.List;
import com.yedam.erp.vo.hr.PayDeductCodeVO;

public interface PayDeductCodeMapper {

	// 목록 조회
	List<PayDeductCodeVO> selectPayDeductList(PayDeductCodeVO param);

	// 단건 조회
	PayDeductCodeVO selectPayDeduct(String deductCode);

	// 등록
	int insertPayDeduct(PayDeductCodeVO vo);

	// 수정
	int updatePayDeduct(PayDeductCodeVO vo);

	// 사용여부 변경
	int updateUseYn(PayDeductCodeVO vo);
}
