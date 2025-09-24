package com.yedam.erp.service;

import java.util.List;

import com.yedam.erp.vo.common.CommonCodeVO;

public interface CommonCodeService {

	List<CommonCodeVO> listCommonCodes(String groupId, String keyword, String useYn);

	CommonCodeVO getCommonCode(String groupId, String codeId);
}
