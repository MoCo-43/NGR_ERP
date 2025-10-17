package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayDeductCodeVO;

public interface PayDeductCodeService {
	//전체조회
	List<PayDeductCodeVO> getDeductList(Long companyCode);
	//단건조회
	PayDeductCodeVO getDeduct(PayDeductCodeVO vo);
	//등록
	int addDeduct(PayDeductCodeVO vo);
	//수정
	int editDeduct(PayDeductCodeVO vo);
	//사용여부
	int changeUseYn(PayDeductCodeVO vo);
}
