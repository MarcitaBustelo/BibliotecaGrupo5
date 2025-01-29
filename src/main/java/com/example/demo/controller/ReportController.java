package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.ReportService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@GetMapping("/mostBorrow")
	public String getMostBorrow(Long userId, Model model) {
		List<Map<String, Object>> mostBorrowedBooks = reportService.getMostBorrowedBooks();
		model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);

		return "reportsMostBorrow";
	}

	@GetMapping("/loanHistory")
	public String getLoanHistory(Long userId, Model model) {
		List<User> users = userService.getAllUsers().stream().filter(user -> !user.getRole().equals("ROLE_ADMIN"))
				.toList();
		model.addAttribute("users", users);

		if (userId != null) {
			List<Loan> userLoanHistory = reportService.getUserLoanHistory(userId);

			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
			List<Map<String, String>> formattedLoanHistory = new ArrayList<>();

			for (Loan loan : userLoanHistory) {
				Map<String, String> formattedLoan = new HashMap<>();
				formattedLoan.put("title", loan.getBook().getTitle());
				formattedLoan.put("initialDate", sdf.format(loan.getInitial_date()));
				formattedLoan.put("dueDate", sdf.format(loan.getDue_date()));
				formattedLoanHistory.add(formattedLoan);
			}

			model.addAttribute("userLoanHistory", formattedLoanHistory);
		}

		long totalUserCount = reportService.getTotalUserCount();
		model.addAttribute("totalUserCount", totalUserCount);

		model.addAttribute("userId", userId);

		return "reportsLoanHistory";
	}

	@GetMapping("/loanHistoryByBook")
	public String getLoanHistoryByBook(@RequestParam(value = "bookId", required = false) Long bookId,
			@RequestParam(value = "userId", required = false) Long userId, Model model) {

		List<Book> books = bookService.listAllBooks();
		model.addAttribute("books", books);

		if (bookId != null) {
			List<User> users = reportService.getUsersByBookId(bookId);
			model.addAttribute("users", users);
		}

		if (bookId != null) {
			List<Loan> bookLoanHistory;
			if (userId != null) {
				bookLoanHistory = reportService.getBookLoanHistoryByUser(bookId, userId);
			} else {
				bookLoanHistory = reportService.getBookLoanHistory(bookId);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
			List<Map<String, String>> formattedBookLoanHistory = new ArrayList<>();

			for (Loan loan : bookLoanHistory) {
				Map<String, String> formattedLoan = new HashMap<>();
				formattedLoan.put("initialDate", sdf.format(loan.getInitial_date()));
				formattedLoan.put("dueDate", sdf.format(loan.getDue_date()));
				formattedLoan.put("userEmail", loan.getUser().getEmail());
				formattedBookLoanHistory.add(formattedLoan);
			}

			model.addAttribute("bookLoanHistory", formattedBookLoanHistory);
		}

		model.addAttribute("bookId", bookId);
		model.addAttribute("userId", userId);

		return "loanPerBookADMIN";
	}

}
