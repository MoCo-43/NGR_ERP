package com.yedam.erp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

	@Test
	public void encode() {
		// Create an encoder with strength 16
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String result = encoder.encode("1234");
		System.out.println(result);
		assertTrue(encoder.matches("1234", result));//패스워드 비교 암호화 후 DB 저장(DB와 사용자가 입력한 비번 비교)
	}
}
