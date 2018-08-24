package com.eric.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.eric.springboot.model.UserInfo;

@Controller
public class IndexController {

	@Autowired
	private UserInfo userInfo;
	
	@Autowired
	FindByIndexNameSessionRepository<? extends Session> sessions;
	
	@GetMapping("/")
	public String index(HttpSession session, @RequestParam("name") String name, @RequestParam("pid") String pid, Model model) {
		session.setAttribute("username", name);
		session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, pid);
		model.addAttribute("username", name);
		model.addAttribute("pid", pid);
		model.addAttribute("sessionTimout", session.getMaxInactiveInterval());
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(new Date());
		c.add(Calendar.SECOND, session.getMaxInactiveInterval());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("sessionexpire", sdf.format(c.getTime()));
		
		return "index";
	}
	
	@GetMapping("/autofill")
	public String autofill(Model model) {
		userInfo.setPid(123456L);
		userInfo.setName("Eric123");
		model.addAttribute("username", userInfo.getName());
		model.addAttribute("pid", String.valueOf(userInfo.getPid()));
		
		return "index";
	}
	
	@GetMapping("/pageOne")
	public String pageOne() {
		return "pageOne";
	}
	
	@GetMapping("/pageClean/{pid}")
	public String pageClean(HttpSession session, @PathVariable("pid") String pid) {
		if (session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME) == null) {
			session.invalidate();
		}
		return "pageOne";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "afterLogout";
	}
	
	@GetMapping("/logout/{pid}")
	public String logoutByPid(HttpSession session, @PathVariable("pid") String pid) {
		Map<String, ? extends Session> sessionMap = sessions.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, pid);
		sessionMap.keySet().stream().forEach(key -> {
			sessions.deleteById(key);
		});
		return "redirect:/pageOne";
	}
}
