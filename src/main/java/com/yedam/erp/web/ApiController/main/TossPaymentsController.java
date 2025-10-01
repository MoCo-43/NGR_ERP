package com.yedam.erp.web.ApiController.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TossPaymentsController {

    // application.properties에서 값을 읽어옴
    @Value("${toss.clientKey}")
    private String clientKey;

    @GetMapping("/api/v1/payments/config")
    public String getClientKey() {
        return clientKey;
    }
}