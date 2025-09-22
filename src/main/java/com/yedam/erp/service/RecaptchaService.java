package com.yedam.erp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yedam.erp.vo.main.RecaptchaVO;

@Service // 1. 이 클래스는 스프링이 관리하는 '서비스'임을 선언
public class RecaptchaService {

    // 2. 필요한 도구와 정보 준비
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${recaptcha.secret-key}")
    private String recaptchaSecretKey;

    @Value("${recaptcha.url}")
    private String recaptchaUrl;

    /**
     * reCAPTCHA 토큰 유효성을 검증하는 핵심 메소드
     * @param recaptchaResponse 프론트엔드에서 전달받은 g-recaptcha-response 값
     * @return 유효하면 true, 아니면 false
     */
    public boolean verifyRecaptcha(String recaptchaResponse) { // 3. 핵심 임무 시작
        
        // 3-1. Google에 보낼 요청서(파라미터) 준비
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", recaptchaSecretKey);
        params.add("response", recaptchaResponse);

        // 3-2. Google에 요청서를 보내고 응답 받기
        RecaptchaVO apiResponse = restTemplate.postForObject(recaptchaUrl, params, RecaptchaVO.class);
        System.out.println("구글 reCAPTCHA 응답: " + apiResponse); // 응답 객체 전체를 출력

        if (apiResponse == null) {
            // 네트워크 문제 등으로 응답을 아예 못 받은 경우
            return false;
        }
        
        // 3-3. 응답 결과에서 가장 중요한 '성공 여부'만 꺼내서 보고(return)
        return apiResponse.isSuccess();
    }
}