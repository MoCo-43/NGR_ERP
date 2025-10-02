package com.yedam.erp.service.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class TossPaymentsService {

    // application.properties에 toss.secretKey 설정 필수
    @Value("${toss.secretKey}")
    private String secretKey; 

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOSS_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * [V2 가이드 준수] Toss Payments 서버에 최종 승인 요청 (POST /v1/payments/confirm)
     */
    public Map<String, Object> confirmPayment(String paymentKey, String orderId, Long amount) throws IOException {
        
        String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("paymentKey", paymentKey);
        requestBody.put("orderId", orderId);
        requestBody.put("amount", amount);

        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(requestBody), JSON);

        Request request = new Request.Builder()
                .url(TOSS_CONFIRM_URL)
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            
            String responseBody = Objects.requireNonNull(response.body()).string();
            Map<String, Object> tossResponse = objectMapper.readValue(responseBody, Map.class);
            
            if (response.code() != 200 || !"DONE".equals(tossResponse.get("status"))) {
                 log.error("Toss 결제 승인 실패 - Code: {}, Message: {}", tossResponse.get("code"), tossResponse.get("message"));
                 tossResponse.put("httpStatusCode", response.code());
                 return tossResponse; 
            }

            log.info("Toss 결제 승인 성공 - PaymentKey: {}", paymentKey);
            return tossResponse;

        } catch (IOException e) {
            log.error("Toss 결제 승인 요청 중 I/O 오류 발생", e);
            throw e;
        }
    }
}