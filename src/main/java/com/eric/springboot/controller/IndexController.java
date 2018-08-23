package com.eric.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eric.springboot.model.UserInfo;

@Controller
public class IndexController {

	@Autowired
	private UserInfo userInfo;
	
	@GetMapping("/")
	public String index(HttpSession session, @RequestParam("name") String name, Model model) {
		if (!StringUtils.isEmpty(name)) {
			session.setAttribute("username", name);
			model.addAttribute("username", name);
		}
		return "index";
	}
	
	@GetMapping("/autofill")
	public String autofill(Model model) {
		userInfo.setPid(123456L);
		userInfo.setName("Eric123");
		model.addAttribute("username", userInfo.getName());
		
		return "index";
	}
	
	@GetMapping("/pageOne")
	public String pageOne() {
		return "pageOne";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "afterLogout";
	}
}
