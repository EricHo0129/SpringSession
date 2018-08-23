package com.eric.springboot.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index(HttpSession session, @RequestParam("name") String name, Model model) {
		if (!StringUtils.isEmpty(name)) {
			session.setAttribute("username", name);
			model.addAttribute("username", name);
		}
		return "index";
	}
	
	@GetMapping("/pageOne")
	public String pageOne() {
		return "pageOne";
	}
}
