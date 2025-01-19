package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	@Qualifier("userService")
	UserServiceImpl userService;

	@Autowired
	@Qualifier("userRepository")
	UserRepository userRepository;

	private static final String LOGIN_VIEW = "login";

	@GetMapping("")
	public String tryLogin(Model model, @RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "success", required = false) String success, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			String errorMessage = (String) session.getAttribute("error");
			if (errorMessage != null) {
				model.addAttribute("error", errorMessage);
				session.removeAttribute("error"); 
			}
		}

		if (success != null && !success.isEmpty()) {
			model.addAttribute("success", success);
		}

		model.addAttribute("logout", logout);
		model.addAttribute("user", new User());

		return LOGIN_VIEW; 
	}
}
