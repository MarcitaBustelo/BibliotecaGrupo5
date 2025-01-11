package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.LoanService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookRepository bookRepository;

	private static final String USER_LOANS = "userLoans";

	@GetMapping("/")
	public String userLoans() {
		return USER_LOANS;
	}

	@GetMapping("/userLoans")
	public String userLoans(Principal principal, Model model) {

		User usu = new User();

		for (User u : userService.getAllUsers()) {
			if (u.getEmail().equals(principal.getName())) {
				usu = u;
			}
		}

		List<Loan> loans = loanService.findLoansByUser(usu);

		model.addAttribute("loans", loans);

		return USER_LOANS;
	}

	@PostMapping("/loanBook/{id}")
	public String loanBook(@RequestParam("id") Long id, Principal principal, RedirectAttributes redirectAttributes) {
	    try {
	        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));

	        if (!book.isAvailable()) {
	            throw new IllegalStateException("Book is not available for loan");
	        }

	        int userLoanCount = loanService.countLoansByUser(principal.getName());
	        if (userLoanCount >= 5) {
	            throw new IllegalStateException("You cannot loan more than 5 books at the same time");
	        }

	        loanService.loanBook(book.getId(), principal.getName());
	        redirectAttributes.addFlashAttribute("success", "Book loaned successfully!");
	    } catch (IllegalArgumentException | IllegalStateException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "An unexpected error occurred. Please try again later.");
	    }
	    return "redirect:/books/listBooks";
	}


	@PostMapping("/return/{id}")
	public String returnBook(@PathVariable("id") Long id, Principal principal, RedirectAttributes redirectAttributes) {
		try {
			loanService.deleteLoan(id);
			redirectAttributes.addFlashAttribute("success", "Book has been returned successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error returning book: " + e.getMessage());
		}
		return "redirect:/loan/userLoans";
	}
}
