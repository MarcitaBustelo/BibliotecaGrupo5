package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Book;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.LoanService;
import com.example.demo.service.ReportService;
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
	private UserRepository userRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private LoanService loanService;

	@GetMapping("/listUsers")
	public ModelAndView listUsers(Authentication authentication) {
		ModelAndView mav = new ModelAndView(USERS_VIEW);
		List<User> users = userService.getAllUsers();
		List<User> filteredUsers = users.stream().filter(user -> !user.getRole().equals("ROLE_ADMIN")).toList();
		mav.addObject("users", (filteredUsers != null) ? filteredUsers : new ArrayList<>());
		return mav;
	}

	@GetMapping("/deleteUsers")
	public String deleteUser(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/admin/listUsers";
	}

	@GetMapping("/updateUsers")
	public String showEditUser(@RequestParam("id") int id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("users", user);
		return "redirect:/admin/listUsers";
	}

	@GetMapping("/bookADMIN")
	public String listBooks(@RequestParam(defaultValue = "0") int page, Model model) {

		int pageSize = 5;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Book> booksPage = bookService.getBooksPaginated(pageable);

		model.addAttribute("books", booksPage.getContent());
		model.addAttribute("currentPage", booksPage.getNumber() + 1);
		model.addAttribute("totalPages", booksPage.getTotalPages());
		model.addAttribute("totalItems", booksPage.getTotalElements());

		return BOOKS_VIEW;
	}

	@GetMapping("/toggleActivation")
	public String toggleActivation(@RequestParam int id) {
		User user = userService.findById(id);
		user.setActivated(!user.getActivated());
		userRepository.save(user);
		return "redirect:/admin/listUsers";
	}

	@GetMapping("/graphics")
	public String getAdminGraphics(Model model) {
		List<Object[]> loansByMonthData = loanService.getLoansByMonth();
		List<Object[]> formattedLoansByMonth = new ArrayList<>();

		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		for (Object[] row : loansByMonthData) {
			int monthNumber = (int) row[0];
			long loanCount = (long) row[1];

			String monthName = monthNames[monthNumber - 1];
			formattedLoansByMonth.add(new Object[] { monthName, loanCount });
		}
		List<Object[]> loansPerUser = loanService.getLoansPerUser();
		List<Object[]> booksByCategoryData = bookService.getBooksByCategory();

		model.addAttribute("loansByMonth", formattedLoansByMonth);
		model.addAttribute("loansPerUser", loansPerUser);
		model.addAttribute("booksByCategory", booksByCategoryData);

		return "graphics";
	}

}