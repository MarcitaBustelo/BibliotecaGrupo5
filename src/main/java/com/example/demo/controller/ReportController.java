package com.example.demo.controller;

import java.util.List;
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
		// Lista de libros más prestados
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
			model.addAttribute("userLoanHistory", userLoanHistory);
		}

		long totalUserCount = reportService.getTotalUserCount();
		model.addAttribute("totalUserCount", totalUserCount);

		model.addAttribute("userId", userId);

		return "reportsLoanHistory";
	}

	@GetMapping("/loanHistoryByBook")
	public String getLoanHistoryByBook(@RequestParam(value = "bookId", required = false) Long bookId,
			@RequestParam(value = "userId", required = false) Long userId, Model model) {

		// Lista de libros
		List<Book> books = bookService.listAllBooks();
		model.addAttribute("books", books);

		// Lista de usuarios relacionados con los préstamos del libro seleccionado
		if (bookId != null) {
			List<User> users = reportService.getUsersByBookId(bookId); // Método para obtener usuarios
			model.addAttribute("users", users);
		}

		// Historial de préstamos filtrado por libro y usuario
		if (bookId != null) {
			List<Loan> bookLoanHistory;
			if (userId != null) {
				bookLoanHistory = reportService.getBookLoanHistoryByUser(bookId, userId); // Filtrado por usuario
			} else {
				bookLoanHistory = reportService.getBookLoanHistory(bookId); // Solo por libro
			}
			model.addAttribute("bookLoanHistory", bookLoanHistory);
		}

		model.addAttribute("bookId", bookId);
		model.addAttribute("userId", userId);

		return "loanPerBookADMIN";
	}

}
