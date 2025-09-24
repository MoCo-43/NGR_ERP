package com.yedam.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yedam.erp.mapper.common.CommonCodeMapper;
import com.yedam.erp.service.CommonCodeService;
import com.yedam.erp.vo.common.CommonCodeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

	private final CommonCodeMapper mapper;

	@Override
	public List<CommonCodeVO> listCommonCodes(String groupId, String keyword, String useYn) {
		if (!StringUtils.hasText(groupId)) {
			throw new IllegalArgumentException("groupId는 필수입니다.");
		}
		// 기본값: 사용(Y)만
		String yn = StringUtils.hasText(useYn) ? useYn : "Y";
		String kw = StringUtils.hasText(keyword) ? keyword.trim() : null;

		return mapper.selectCommonCodes(groupId.trim(), kw, yn);
	}

	@Override
	public CommonCodeVO getCommonCode(String groupId, String codeId) {
		if (!StringUtils.hasText(groupId) || !StringUtils.hasText(codeId)) {
			throw new IllegalArgumentException("groupId, codeId는 필수입니다.");
		}
		return mapper.selectCommonCode(groupId.trim(), codeId.trim());
	}
}