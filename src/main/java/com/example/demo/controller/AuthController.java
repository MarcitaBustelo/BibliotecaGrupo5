package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@GetMapping("/")
	public String home() {
		return "welcome";
	}

	@GetMapping("/about")
	public String aboutUs() {
		return "about";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
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
		return "redirect:/";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute User user, RedirectAttributes flash, Model model) {
		User existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null && userService.verifyPassword(user.getPassword(), existingUser.getPassword())) {
			flash.addFlashAttribute("success", "Welcome, " + existingUser.getName() + "!");
			return "redirect:/";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "login";
		}
	}

}
