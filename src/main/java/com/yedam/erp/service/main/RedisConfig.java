package com.yedam.erp.service.main;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	//레디스: 인메모리 데이터베이스,데이터를 메모리에 저장 후 읽고 쓸수 있는 캐시/저장소(인증번호 저장후 사용자 입력값과 비교 위해 사용)
    @Bean
    //스프링에서 데이터를 읽고 쓰기 위해 이 부분 필요
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
       //스프링이 자동으로 구성한 RedisConnectionFactory를 주입받은 후 레디스 서버와 실제 연결을 맺는 부분으로 ip,port,패스워드 등 설정은 어플리케이션에서 해준다.
    	RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        //레디스 명령어를 자바 코드로 쉽게 다룰 수 있는 인터페이스 부분
        // Key/Value를 문자열(String)로 직렬화(Serialization),레디스는 데이터를 저장할 때 바이너리로 저장하기때문에 StringRedisSerializer로 문자열 형태로 저장되도록 한다.
        StringRedisSerializer serializer = new StringRedisSerializer();
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        
        return template;
    }
}