package com.eric.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eric.springboot.interceptor.LogonInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//除了登入頁之外,都要經過此攔截器
		registry.addInterceptor(new LogonInterceptor())
		.addPathPatterns("/*")
		.excludePathPatterns("/login","/sso/*","/user");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
