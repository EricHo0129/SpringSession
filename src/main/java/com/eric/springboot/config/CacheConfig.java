package com.eric.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class CacheConfig {

	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		//關閉此選項
		return ConfigureRedisAction.NO_OP;
	}
}
