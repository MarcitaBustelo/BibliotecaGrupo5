package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.UserModel;
import com.example.demo.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	@Qualifier("userService")
	UserServiceImpl userService;

	private static final String REGISTER_FORM = "register";

	@GetMapping("")
	public String showForm(Model model) {
		model.addAttribute("user", new UserModel());
		return REGISTER_FORM;
	}

	@PostMapping("/send")
	public String sendForm(@Valid @ModelAttribute("user") UserModel userModel, BindingResult bResult,
			RedirectAttributes flash) {
		userService.validateRegister(userModel, bResult);

		if (bResult.hasErrors())
			return REGISTER_FORM;

		flash.addFlashAttribute("success", "You've been registered successfully!");
		userService.register(userModel);
		return "redirect:/login";
	}
}