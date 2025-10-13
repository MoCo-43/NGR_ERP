package com.yedam.erp.mapper.main;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.yedam.erp.vo.main.PwResetTokenVO;

@Mapper
public interface PwResetTokenMapper {
    /**
     * 비밀번호 재설정 토큰 정보를 DB에 저장합니다.
     * @param tokenVO 저장할 토큰 정보
     */
    void save(PwResetTokenVO tokenVO);
    
    //토큰 문자열(UUID)기준 토큰 정보 조회.
    PwResetTokenVO findTokenByTokenString(@Param("token")String token);
    //만료토큰 DB삭제
    void deleteToken(@Param("token")String token);
}