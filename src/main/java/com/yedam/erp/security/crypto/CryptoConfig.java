//package com.yedam.erp.security.crypto;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.yedam.erp.security.crypto.CryptoService;
//
//@Configuration
//public class CryptoConfig {
//
//    @Bean
//    public CryptoService cryptoService(
//            @Value("${security.crypto.aes-key-base64}") String aesKeyBase64) {
//        return new CryptoService(aesKeyBase64);
//    }
//}
