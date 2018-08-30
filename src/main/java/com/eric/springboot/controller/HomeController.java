package com.eric.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.eric.springboot.model.UserInfo;

@Controller
public class HomeController {
	
	@Autowired
	private UserInfo userInfo;
	
	@GetMapping("/index")
	public String home(Model model) {
		UserInfo user = new UserInfo();
		BeanUtils.copyProperties(userInfo, user);
		model.addAttribute("user", user);
		return "home";
	}
	
	@PostMapping("/fillOut")
	public String fillOut(HttpSession session, UserInfo user, Model model) {
		BeanUtils.copyProperties(user, userInfo);
		model.addAttribute("sessionexpire", getSessionexpire(session));
		return "user";
	}
	
	@GetMapping("/user")
	public String getUserInfo(HttpSession session, Model model) {
		model.addAttribute("sessionexpire", getSessionexpire(session));
		return "user";
	}
	
	private String getSessionexpire(HttpSession session) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(new Date());
		c.add(Calendar.SECOND, session.getMaxInactiveInterval());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}
}
