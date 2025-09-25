
package com.yedam.erp.service.hr;

import java.util.List;

import com.yedam.erp.vo.hr.PayDeductCodeVO;

public interface PayDeductCodeService {

	//목록 조회
	List<PayDeductCodeVO> getList(String useYn, String deductName);

	//단건 조회
	PayDeductCodeVO get(String deductCode);

	//등록
	boolean create(PayDeductCodeVO vo);

	//수정
	boolean update(PayDeductCodeVO vo);

	//사용여부 변경
	boolean changeUseYn(String deductCode, String useYn);
}
