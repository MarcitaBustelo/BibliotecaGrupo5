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
	public String tryLogin(Model model, @RequestParam(value = "error", required = false) String error,
	                       @RequestParam(value = "logout", required = false) String logout,
	                       @RequestParam(value = "email", required = false) String email) {
	    model.addAttribute("error", error);
	    model.addAttribute("logout", logout);
	    model.addAttribute("user", new User());

	    if (email != null) {
	        User user = userRepository.findByEmail(email);
	        
	        if (user != null && !user.getActivated()) {
	            model.addAttribute("error", "Your account is not activated. Cannot login.");
	            return LOGIN_VIEW;
	        }
	    }

	    return LOGIN_VIEW;
	}

}