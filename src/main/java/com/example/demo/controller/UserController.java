package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userRepository")
	UserRepository userRepository;

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@GetMapping("/myaccount")
	public ModelAndView getMyAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName(); // Esto te dará el "username", usualmente el email
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		}

		ModelAndView mav = new ModelAndView("myaccount");
		mav.addObject("user", user);
		return mav;
	}

	@GetMapping("/updateUsers")
	public String showEditLibroForm(@RequestParam("id") int id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("users", user);
		return "userForm";
	}

}
