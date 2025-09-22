package com.yedam.erp.vo.main;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RecaptchaVO {

	//reCAPTCHA 검증 성공 여부 (t/f)
	private boolean success;
	//사용자 통과한 시간(ISO 8061형식)
	private String challenge_ts;
	//reCAPTCHA가 사용된 웹사이트 호스트 이름
	private String hostname;
	
	//검증 실패시 에러 코드 목록,json의 error-codes와 java errorCodes 매핑된다.
    @JsonProperty("error-codes")
    private List<String> errorCodes;
}
