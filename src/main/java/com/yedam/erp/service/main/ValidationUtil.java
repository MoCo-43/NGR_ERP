package com.yedam.erp.service.main;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    /**
     * 6자리 숫자 인증 코드를 생성합니다.
     * @return 6자리 숫자 문자열
     */
    public String createCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 100000 ~ 999999
        return String.valueOf(code);
    }
}