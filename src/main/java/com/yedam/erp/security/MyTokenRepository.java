package com.yedam.erp.security;

import java.util.Date;
import javax.sql.DataSource;
import java.sql.*;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class MyTokenRepository implements PersistentTokenRepository {//스프링 시큐리티 리멤버미기능 커스텀 PersistentTokenRepository,테이블 로그인 상태를 DB에 저장후 관리

    private final DataSource dataSource; //DB연결 

    public MyTokenRepository(DataSource dataSource) {//DB연결시 dataSource 이용
        this.dataSource = dataSource;
    }

    // username → emp_id_no 조회 -> 정수형 반환
    private Integer getEmpIdNo(String username, Connection conn) throws SQLException {
        String sql = "SELECT emp_id_no FROM emp_login WHERE emp_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("emp_id_no");
            } else {
                throw new SQLException("No emp_id_no found for username: " + username);
            }
        }
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String sql = "INSERT INTO remember_login (series, token, last_used, emp_id_no) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Integer empIdNo = getEmpIdNo(token.getUsername(), conn);

            ps.setString(1, token.getSeries());
            ps.setString(2, token.getTokenValue());
            ps.setTimestamp(3, new Timestamp(token.getDate().getTime()));
            ps.setInt(4, empIdNo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//기존 토큰 갱신 시 호출 -> 로그인 성공 remember-me 쿠키가 존재할 경우 token값과 LAST_USED가 업데이트 되고,SERIES값 특정 토큰을 구별한다.
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String sql = "UPDATE remember_login SET token = ?, last_used = ? WHERE series = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tokenValue);
            ps.setTimestamp(2, new Timestamp(lastUsed.getTime()));
            ps.setString(3, series);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //쿠키 읽어 db에서 토큰 조회할때 호출하여 사용됨
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String sql = "SELECT emp_id_no, series, token, last_used FROM remember_login WHERE series = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, seriesId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // emp_id_no → username 변환
                String username = getUsernameByEmpId(rs.getInt("emp_id_no"), conn);
                return new PersistentRememberMeToken(
                        username,
                        rs.getString("series"),
                        rs.getString("token"),
                        rs.getTimestamp("last_used")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //로그아웃 시 remember-me관련 토큰이 삭제 된다.
    @Override
    public void removeUserTokens(String username) {
        String sql = "DELETE FROM remember_login WHERE emp_id_no = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Integer empIdNo = getEmpIdNo(username, conn);
            ps.setInt(1, empIdNo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // emp_id_no 기반으로 username 조회,토큰을 다시 인증해서 사용하려면 필요
    private String getUsernameByEmpId(int empIdNo, Connection conn) throws SQLException {
        String sql = "SELECT emp_id FROM emp_login WHERE emp_id_no = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empIdNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("emp_id");
            } else {
                throw new SQLException("No username found for emp_id_no: " + empIdNo);
            }
        }
    }
}
