package com.eric.springboot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogonInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession(false);
		//關鍵字,如果有這個識別符,表示是經由登入成功而來
		if (session == null || session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME) == null) {				
			response.sendRedirect("/login");
			return false; //後面的chain不用繼續了
		}
		
		return super.preHandle(request, response, handler);
	}
}
