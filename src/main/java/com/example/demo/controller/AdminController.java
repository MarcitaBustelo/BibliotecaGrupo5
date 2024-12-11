package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.models.BookModel;
import com.example.demo.models.UserModel;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final String USERS_VIEW = "users";
	private static final String USERS_FORM = "userForm";
	private static final String BOOKS_VIEW = "bookADMIN";


	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;

	@GetMapping("/listUsers")
	public ModelAndView listUsers(Authentication authentication) {
		ModelAndView mav = new ModelAndView(USERS_VIEW);
		List<User> users = userService.getAllUsers();
		List<User> filteredUsers = users.stream().filter(user -> !user.getRole().equals("ROLE_ADMIN")).toList();
		mav.addObject("users", (filteredUsers != null) ? filteredUsers : new ArrayList<>());
		return mav;
	}

	@GetMapping("/deleteUsers")
	public String deleteCourse(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/users/listUsers";
	}

	@GetMapping("/updateUsers")
	public String showEditLibroForm(@RequestParam("id") int id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("users", user);
		return "redirect:/users/listUsers";
	}

	@GetMapping("/bookADMIN")
	public ModelAndView listBooksADMIN(Authentication authentication) {
		ModelAndView mav = new ModelAndView(BOOKS_VIEW);
		List<BookModel> books = bookService.listAllBooks();
		mav.addObject("books", (books != null) ? books : new ArrayList<BookModel>());
		return mav;
	}
}
