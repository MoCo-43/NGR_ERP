//package com.yedam.erp.security.crypto;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.GCMParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.SecureRandom;
//import java.util.Base64;
//
//public class CryptoService {
//
//    private static final String ALGORITHM = "AES/GCM/NoPadding";
//    private static final int GCM_TAG_LENGTH = 128; // bits
//    private static final int IV_LENGTH = 12; // bytes
//    private final byte[] keyBytes;
//
//    public CryptoService(String base64Key) {
//        this.keyBytes = Base64.getDecoder().decode(base64Key);
//    }
//
//    // ✅ Base64 유효성 검증 함수 추가
//    private boolean isBase64(String str) {
//        try {
//            Base64.getDecoder().decode(str);
//            return true;
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    // 암호화
//    public String encrypt(String plainText) {
//        if (plainText == null || plainText.isEmpty()) return null;
//        try {
//            byte[] iv = new byte[IV_LENGTH];
//            new SecureRandom().nextBytes(iv);
//
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
//
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
//            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
//
//            byte[] encrypted = new byte[IV_LENGTH + cipherText.length];
//            System.arraycopy(iv, 0, encrypted, 0, IV_LENGTH);
//            System.arraycopy(cipherText, 0, encrypted, IV_LENGTH, cipherText.length);
//
//            return Base64.getEncoder().encodeToString(encrypted);
//        } catch (Exception e) {
//            throw new RuntimeException("AES-GCM encryption error", e);
//        }
//    }
//
//    // ✅ 안전한 복호화 (평문일 경우 그대로 반환)
//    public String decrypt(String base64Text) {
//        if (base64Text == null || base64Text.isEmpty()) return null;
//
//        // 평문이면 그대로 반환
//        if (!isBase64(base64Text)) {
//            return base64Text;
//        }
//
//        try {
//            byte[] encrypted = Base64.getDecoder().decode(base64Text);
//            byte[] iv = new byte[IV_LENGTH];
//            System.arraycopy(encrypted, 0, iv, 0, IV_LENGTH);
//            byte[] cipherText = new byte[encrypted.length - IV_LENGTH];
//            System.arraycopy(encrypted, IV_LENGTH, cipherText, 0, cipherText.length);
//
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
//
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
//            byte[] plainText = cipher.doFinal(cipherText);
//
//            return new String(plainText, "UTF-8");
//        } catch (Exception e) {
//            throw new RuntimeException("AES-GCM decryption error", e);
//        }
//    }
//}
