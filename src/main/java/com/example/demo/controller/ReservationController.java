package com.example.demo.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookRepository bookRepository;

	private static final String USER_RESERVATIONS = "userReservations";

	@GetMapping("/")
	public String userReservations() {
		return USER_RESERVATIONS;
	}

	@GetMapping("/userReservation")
	public String userReservations(Principal principal, Model model) {

		User usu = new User();

		for (User u : userService.getAllUsers()) {
			if (u.getEmail().equals(principal.getName())) {
				usu = u;
			}
		}

		List<Reservation> reservations = reservationService.findReservationsByUser(usu);

		model.addAttribute("reservations", reservations);

		return USER_RESERVATIONS;
	}

	@GetMapping("/adminReservations")
	public String adminReservations(@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate, Model model) {

		List<Reservation> reservations;

		try {
			Date startDate = (fromDate != null) ? Date.valueOf(fromDate) : null;
			Date endDate = (toDate != null) ? Date.valueOf(toDate) : null;

			if (startDate != null && endDate != null) {
				reservations = reservationService.findReservationsBetween(startDate, endDate);
			} else {
				reservations = reservationService.listAllReservations();
			}

			model.addAttribute("fromDate", fromDate);
			model.addAttribute("toDate", toDate);
		} catch (Exception e) {
			reservations = reservationService.listAllReservations();
		}

		model.addAttribute("reservations", reservations);
		return "adminReservations";
	}

	@PostMapping("/reserve/{id}")
	public String reserveBook(@PathVariable("id") Long id, Principal principal, RedirectAttributes redirectAttributes) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));

		try {
			reservationService.reserveBook(book.getId(), principal.getName());
			redirectAttributes.addFlashAttribute("success", "Book reserved successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/books/listBooks";
	}

	@PostMapping("/cancel/{id}")
	public String cancelReservation(@PathVariable("id") Long id, Principal principal,
			RedirectAttributes redirectAttributes) {
		try {
			reservationService.deleteReservation(id);
			redirectAttributes.addFlashAttribute("success", "Reservation deleted successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error deleting reservation: " + e.getMessage());
		}
		return "redirect:/reservation/userReservation";
	}

	@PostMapping("/delete/{id}")
	public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			reservationService.deleteReservation(id);
			redirectAttributes.addFlashAttribute("success", "Reservation deleted successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error deleting reservation: " + e.getMessage());
		}
		return "redirect:/reservation/adminReservations";
	}

}