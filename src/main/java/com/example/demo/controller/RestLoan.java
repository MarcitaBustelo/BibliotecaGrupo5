package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.service.LoanService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class RestLoan {

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserService userService;

	@PostMapping("/loan/{bookId}")
	public ResponseEntity<Map<String, Object>> loanBook(@PathVariable Long bookId,
			@RequestBody Map<String, String> request) {
		String email = request.get("email");

		Optional<User> userOp = userService.findByEmail(email);

		User user = userOp.get();

		Map<String, Object> data = new HashMap<>();
		data.put("userId", user.getId());
		data.put("bookId", bookId);

		Map<String, Object> response = new HashMap<>();
		if (email != null) {
			loanService.loanBook(bookId, user.getId());
			response.put("success", true);
			response.put("message", "User with email " + email + " has loaned book with ID: " + bookId);
			response.put("data", data);

			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "Book or user not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@PostMapping("/return/{loanId}")
	public ResponseEntity<Map<String, Object>> returnBook(@PathVariable Long loanId) {
		Optional<Loan> loan = loanService.findById(loanId);

		User user = loan.get().getUser();
		Book book = loan.get().getBook();

		Map<String, Object> data = new HashMap<>();
		data.put("userId", user.getId());
		data.put("bookId", book.getId());
		data.put("ini date", loan.get().getInitial_date());
		data.put("due date", loan.get().getDue_date());

		Map<String, Object> response = new HashMap<>();
		if (loan.isPresent()) {
			loanService.deleteLoan(loanId);
			response.put("success", true);
			response.put("message",
					"User with email " + user.getEmail() + " has returned book with ID: " + book.getId());
			response.put("data", data);
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "Loan not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}