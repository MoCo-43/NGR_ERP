package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayDeductCodeVO;

public interface PayDeductCodeService {

	List<PayDeductCodeVO> getDeductList(Long companyCode);

	PayDeductCodeVO getDeduct(PayDeductCodeVO vo);

	int addDeduct(PayDeductCodeVO vo);

	int editDeduct(PayDeductCodeVO vo);

	int changeUseYn(PayDeductCodeVO vo);
}
