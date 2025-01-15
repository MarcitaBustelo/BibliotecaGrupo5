package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.service.ReportService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String getAdminReports(Long userId, Model model) {
		// Lista de libros más prestados
		List<Map<String, Object>> mostBorrowedBooks = reportService.getMostBorrowedBooks();
		model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);

		// Lista de usuarios (excluyendo al administrador)
		List<User> users = userService.getAllUsers().stream().filter(user -> !user.getRole().equals("ROLE_ADMIN"))
				.toList();
		model.addAttribute("users", users);

		// Historial de préstamos de un usuario específico
		if (userId != null) {
			List<Loan> userLoanHistory = reportService.getUserLoanHistory(userId);
			model.addAttribute("userLoanHistory", userLoanHistory);
		}

		// Número total de usuarios registrados
		long totalUserCount = reportService.getTotalUserCount();
		model.addAttribute("totalUserCount", totalUserCount);

		// Asegurar que el usuario seleccionado se envía al modelo
		model.addAttribute("userId", userId);

		return "reports";
	}
}
