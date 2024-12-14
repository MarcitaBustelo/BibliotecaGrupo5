package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.models.UserModel;
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
		String email = authentication.getName();
		User user = userRepository.findByEmail(email);
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

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		System.out.println("1-" + user);
		model.addAttribute("users", user);
		return "userForm"; 
	}

	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute("users") User user, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		User currentUser = userRepository.findByEmail(email);

		currentUser.setName(user.getName());
		currentUser.setLastname(user.getLastname());

		userService.edit(currentUser);

		return "redirect:/myaccount"; 
	}

}
