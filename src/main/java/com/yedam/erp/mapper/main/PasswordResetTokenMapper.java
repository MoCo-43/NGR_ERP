package com.yedam.erp.mapper.main;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.erp.vo.main.PwResetTokenVO;

@Mapper 
public interface PasswordResetTokenMapper {
    /**
     * 새로운 비밀번호 재설정 토큰 정보를 DB에 저장합니다.
     * @param tokenVO 저장할 토큰 정보 (empIdNo, token, endDate 등)
     */
    void save(PwResetTokenVO tokenVO);

    /**
     * 고유한 토큰 문자열을 사용하여 토큰 정보를 조회합니다.
     * @param token 사용자가 이메일 링크로 받은 토큰 문자열
     * @return 조회된 토큰 정보 객체, 없으면 null
     */
    PwResetTokenVO findByToken(String token);

    /**
     * 사용이 완료된 토큰을 DB에서 삭제합니다.
     * @param token 삭제할 토큰 문자열
     */
    void deleteByToken(String token);
}
