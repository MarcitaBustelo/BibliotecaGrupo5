package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

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
			return "redirect:/";

		}

		model.addAttribute("logout", logout);
		model.addAttribute("user", new User());

		return LOGIN_VIEW;
	}
}
