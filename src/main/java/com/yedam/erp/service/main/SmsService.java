package com.yedam.erp.service.main; // 실제 프로젝트 패키지 경로에 맞게 수정

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;

@Service
public class SmsService {

    @Value("${solapi.api.key}")
    private String apiKey;

    @Value("${solapi.api.secret}")
    private String apiSecret;

    @Value("${solapi.from.number}")
    private String fromNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = SolapiClient.INSTANCE.createInstance(apiKey, apiSecret);
    }

    /**
     * 단일 메시지를 발송하는 메서드
     * @param to 수신번호
     * @param text 전송할 메시지 내용
     * @return 발송 성공 시 true, 실패 시 false
     */
    public boolean sendSms(String to, String text) {
        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(to);
        message.setText(text);

        try {
            this.messageService.send(message);
            System.out.println("SMS 발송 성공: " + to);
            return true;
        } catch (Exception e) {
            System.err.println("SMS 발송 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}