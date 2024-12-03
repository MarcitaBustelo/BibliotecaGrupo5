package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.services.impl.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	private final UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Inyección por constructor
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String home(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			model.addAttribute("authenticated", true);
			model.addAttribute("username", authentication.getName());
		} else {
			model.addAttribute("authenticated", false);
		}
		return "welcome";
	}

	@GetMapping("/about")
	public String about(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			model.addAttribute("authenticated", true);
			model.addAttribute("username", authentication.getName());
		} else {
			model.addAttribute("authenticated", false);
		}
		return "about";
	}

	@GetMapping("/loginForm")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "loginForm";
	}

	@GetMapping("/user")
	public String user(Model model) {
		model.addAttribute("user", new User());
		return "user";
	}

	@GetMapping("/registerForm")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "registerForm";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute User user, BindingResult bindingResult, RedirectAttributes flash) {
		if (bindingResult.hasErrors()) {
			return "registerForm";
		}

		userService.registrar(user);
		flash.addFlashAttribute("success", "User registered successfully!");
		return "redirect:/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute User user, RedirectAttributes flash, Model model) {
		User existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
			flash.addFlashAttribute("success", "Welcome, " + existingUser.getName() + "!");
			return "redirect:/";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "login";
		}
	}
}
