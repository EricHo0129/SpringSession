package com.eric.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eric.springboot.model.UserInfo;

@Controller
public class HomeController {
	
	@Autowired
	private UserInfo userInfo;
	
	@GetMapping("/index")
	public String home() {
		return "home";
	}
	
	@PostMapping("/changeName")
	public String changeName(@RequestParam("name") String name) {
		userInfo.setName(name);
		return "home";
	}
}
