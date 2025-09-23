package com.yedam.erp.service.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class NaverCaptchaService {

    @Value("${naver.captcha.client-id}")
    private String clientId;

    @Value("${naver.captcha.client-secret}")
    private String clientSecret;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getCaptchaKey() throws IOException {
        String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=0";
        Request request = new Request.Builder()
                .url(apiURL)
                .addHeader("X-Naver-Client-Id", clientId)
                .addHeader("X-Naver-Client-Secret", clientSecret)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            System.out.println("dddd"+jsonNode);
            // 'key' 필드가 존재하는지 확인하고, 없으면 예외를 발생시킵니다.
            JsonNode keyNode = jsonNode.get("key");
            if (keyNode != null) {
                return keyNode.asText();
            } else {
                // API에서 정상적인 응답이 아닌 오류를 반환한 경우
                System.err.println("네이버 CAPTCHA API 오류 응답: " + responseBody);
                throw new IOException("Failed to get CAPTCHA key. Check API credentials or service status.");
            }
        }
    }

    public byte[] getCaptchaImage(String key) throws IOException {
        String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
        Request request = new Request.Builder()
                .url(apiURL)
                .addHeader("X-Naver-Client-Id", clientId)
                .addHeader("X-Naver-Client-Secret", clientSecret)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).bytes();
        }
    }

    public boolean validateCaptcha(String key, String value) throws IOException {
        String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=1&key=" + key + "&value=" + value;
        Request request = new Request.Builder()
                .url(apiURL)
                .addHeader("X-Naver-Client-Id", clientId)
                .addHeader("X-Naver-Client-Secret", clientSecret)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // 'result' 필드가 존재하는지 먼저 확인
            JsonNode resultNode = jsonNode.get("result");
            if (resultNode != null) {
                return resultNode.asBoolean();
            } else {
                System.err.println("네이버 CAPTCHA API 검증 오류 응답: " + responseBody);
                return false;
            }
        }
    }
}