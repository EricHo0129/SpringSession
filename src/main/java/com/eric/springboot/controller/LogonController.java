package com.eric.springboot.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eric.springboot.model.LoginInfo;
import com.eric.springboot.model.UserInfo;

@Controller
public class LogonController {

	@Autowired
	private UserInfo userInfo;
	
	@Autowired
	RedisOperationsSessionRepository sessionRepository;
	
	/**
	 * 登入頁
	 * @return
	 */
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginInfo", new LoginInfo());
		return "login";
	}
	
	/**
	 * 登入後回到產品的位置(此處寫入session)
	 * @return
	 */
	@PostMapping("/sso/saml-consume")
	public String consume(HttpSession session, LoginInfo loginInfo) {
		String pidStr = String.valueOf(loginInfo.getPid());
		session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, pidStr);
		session.setAttribute("pid", pidStr);
		session.setAttribute("lastCheckTime", System.currentTimeMillis());
		userInfo.setName(loginInfo.getName());
		return "redirect:/index";
	}
	
	@GetMapping("/sso/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping("/sso/logout/{pid}")
	public String logoutByPid(HttpSession session, @PathVariable("pid") String pid, Model model) {
		Map<String, ? extends Session> sessionMap = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, pid);
		sessionMap.keySet().stream().forEach(key -> {
			sessionRepository.deleteById(key);
		});
		model.addAttribute("pid", pid);
		return "logout";
	}
}
