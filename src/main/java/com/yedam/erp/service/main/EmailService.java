package com.yedam.erp.service.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /**
     * Spring Boot가 application.properties의 설정을 읽어
     * 자동으로 생성해주는 메일 발송 객체입니다.
     */
    @Autowired
    private JavaMailSender emailSender;

    /**
     * 간단한 텍스트 이메일을 발송하는 메서드입니다.
     * @param to 받는 사람 이메일 주소
     * @param subject 이메일 제목
     * @param text 이메일 본문
     */
    public void sendSimpleMessage(String to, String subject, String text) {
        // 이메일 내용을 담을 객체 생성
        SimpleMailMessage message = new SimpleMailMessage();

        // 보내는 사람 이메일. application.properties에 설정한 username과 동일하게 하는 것을 권장합니다.
        message.setFrom("your-email@example.com");
        // 받는 사람
        message.setTo(to);
        // 제목
        message.setSubject(subject);
        // 내용
        message.setText(text);

        // 메일 발송
        emailSender.send(message);
    }
}