package com.yedam.erp.service.main;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@Slf4j
public class TossPaymentsService {

    // application.properties에 toss.secretKey 설정 필수
    @Value("${toss.secretKey}")
    private String secretKey; 
    @Value("${toss.biling.secretKey}")
    private String bilingSecretKey;
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOSS_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";
    private static final String TOSS_BILLING_URL = "https://api.tosspayments.com/v1/billing/";
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
    public Map<String, Object> approveBillingPayment(String billingKey, String customerKey, Long amount, String orderId) throws IOException {
        // 빌링키 결제는 Basic 인증 키가 다릅니다.
        String encodedAuth = Base64.getEncoder().encodeToString((bilingSecretKey + ":").getBytes());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("customerKey", customerKey);
        requestBody.put("amount", amount);
        requestBody.put("orderId", orderId);
        requestBody.put("orderName", "NGR_ERP 정기 구독");

        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(requestBody), JSON);
        
        // URL에 billingKey를 포함합니다.
        Request request = new Request.Builder()
                .url(TOSS_BILLING_URL + billingKey)
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/json")
                .post(body)
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            Map<String, Object> tossResponse = objectMapper.readValue(responseBody, Map.class);

            if (response.code() != 200 || !"DONE".equals(tossResponse.get("status"))) {
                 log.error("Toss 빌링키 결제 실패 - OrderId: {}, Code: {}, Message: {}", orderId, tossResponse.get("code"), tossResponse.get("message"));
                 tossResponse.put("httpStatusCode", response.code());
                 return tossResponse;
            }

            log.info("Toss 빌링키 결제 성공 - OrderId: {}", orderId);
            return tossResponse;
        } catch (IOException e) {
            log.error("Toss 빌링키 결제 요청 중 I/O 오류 발생 - OrderId: {}", orderId, e);
            throw e;
        }
    }
}