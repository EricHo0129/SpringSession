package com.eric.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.eric.springboot.model.UserInfo;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
public class CacheConfig {

	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		//關閉此選項(不監聽session銷毀事件)
		return ConfigureRedisAction.NO_OP;
	}
	
	// ---------------- 以下的設定是將寫入Session的Bean序列化為Json的機制 ---------------- // 
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(cf);
		return redisTemplate;
	}
	
	@Bean
    public RedisOperationsSessionRepository sessionRepository(RedisTemplate<Object, Object> redisTemplate) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(redisTemplate);
        sessionRepository.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        sessionRepository.setDefaultMaxInactiveInterval(60);
        return sessionRepository;
    }
}
