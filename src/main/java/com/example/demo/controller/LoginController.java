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
	public String tryLogin(
	        Model model,
	        @RequestParam(value = "error", required = false) String error,
	        @RequestParam(value = "logout", required = false) String logout,
	        @RequestParam(value = "email", required = false) String email,
	        @RequestParam(value = "success", required = false) String success) {

		System.out.println("Error: " + error);
		System.out.println("Email: " + email);
		
	    model.addAttribute("logout", logout);
	    model.addAttribute("user", new User());
	    model.addAttribute("error", error);
	    model.addAttribute("email", email);
	    model.addAttribute("success", success);


	    return LOGIN_VIEW;
	}


}