//package com.yedam.erp.web.ApiController.main;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//@RestController
//@RequestMapping("/api/payment")
//public class WidgetController {
//
//	private static final Logger log = LoggerFactory.getLogger(WidgetController.class);
//
//	
//    @Value("${toss.secretKey}")
//    private String widgetSecretKey;
//
//    @PostMapping("/confirm")
//    public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody Map<String, Object> body) {
//
//        // 클라이언트에서 전달받은 값
//        String paymentKey = (String) body.get("paymentKey");
//        String orderId    = (String) body.get("orderId");
//        String amount     = String.valueOf(body.get("amount"));
//
//        // Toss API 요청 JSON 만들기
//        Map<String, Object> requestMap = new HashMap<>();
//        requestMap.put("paymentKey", paymentKey);
//        requestMap.put("orderId", orderId);
//        requestMap.put("amount", amount);
//        log.info("Loaded Secret Key: [{}]", widgetSecretKey); 
//
//        // 인증 헤더 (Secret Key Base64)
//        String authHeader = "Basic " + Base64.getEncoder()
//                .encodeToString((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
//        log.info("Generated Auth Header: [{}]", authHeader);
//
//        // HTTP 요청 헤더
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", authHeader);
//
//        // RestTemplate 사용해 Toss API 호출
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(
//                "https://api.tosspayments.com/v1/payments/confirm",
//                entity,
//                Map.class
//        );
//
//        // 그대로 반환 (성공 시 200, 실패 시 Toss 응답 코드 그대로)
//        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
//    }
//}
package com.yedam.erp.web.ApiController.main;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/payment")
public class WidgetController {

    private static final Logger log = LoggerFactory.getLogger(WidgetController.class);

    @Value("${toss.secretKey}")
    private String widgetSecretKey;

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody Map<String, Object> body) {

        // --- 로그 추가 시작 ---
        log.info(">>>>>> /api/payment/confirm 엔드포인트 호출됨 <<<<<<");
        log.info("클라이언트로부터 받은 원본 데이터: {}", body);
        // --- 로그 추가 끝 ---

        // 클라이언트에서 전달받은 값
        String paymentKey = (String) body.get("paymentKey");
        String orderId    = (String) body.get("orderId");
        Integer amount    = (Integer) body.get("amount"); // Integer 타입 유지

        // --- 로그 추가 시작 ---
        log.info("파싱된 paymentKey: [{}]", paymentKey);
        log.info("파싱된 orderId:    [{}]", orderId);
        log.info("파싱된 amount:     [{}]", amount);
        // --- 로그 추가 끝 ---

        // Toss API 요청 JSON 만들기
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("paymentKey", paymentKey);
        requestMap.put("orderId", orderId);
        requestMap.put("amount", amount);
        
        log.info("Toss API로 보낼 최종 데이터: {}", requestMap);
        log.info("로드된 Secret Key: [{}]", widgetSecretKey);

        // ... 이하 코드는 이전과 동일합니다 ...
        String authHeader = "Basic " + Base64.getEncoder()
                .encodeToString((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.tosspayments.com/v1/payments/confirm",
                    entity,
                    Map.class
            );
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            log.error("Toss API 호출 실패", e);
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Toss API call failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }
}