package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@GetMapping("/myaccount")
	public ModelAndView getMyAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userService.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		}

		ModelAndView mav = new ModelAndView("myaccount");
		mav.addObject("user", user);
		return mav;
	}

	@GetMapping("/updateUser")
	public String showEditUserForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		User user = userService.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		model.addAttribute("users", user);
		return "userForm";
	}

	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute("users") User user, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentEmail = authentication.getName();

		User currentUser = userService.findByEmail(currentEmail);

		currentUser.setName(user.getName());
		currentUser.setLastname(user.getLastname());
		currentUser.setEmail(user.getEmail());

		userService.edit(currentUser);

		Authentication newAuth = new UsernamePasswordAuthenticationToken(currentUser.getEmail(), //
				authentication.getCredentials(), authentication.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		return "redirect:/myaccount";
	}

	@GetMapping("/changePassword")
	public String showChangePasswordForm() {
		return "changePassword";
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
			RedirectAttributes redirectAttributes) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User currentUser = userService.findByEmail(email);

		if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
			redirectAttributes.addFlashAttribute("error", "The current password is incorrect.");
			return "redirect:/changePassword";
		}

		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "The new passwords do not match.");
			return "redirect:/changePassword";
		}

		if (passwordEncoder.matches(newPassword, currentUser.getPassword())) {
			redirectAttributes.addFlashAttribute("error",
					"The new password cannot be the same as the current password.");
			return "redirect:/changePassword";
		}

		currentUser.setPassword(passwordEncoder.encode(newPassword));
		userService.edit(currentUser);
//comiteo
		redirectAttributes.addFlashAttribute("success", "Password updated successfully.");
		return "redirect:/myaccount";
	}

}
