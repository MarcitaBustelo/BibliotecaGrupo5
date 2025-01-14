package com.example.demo.services.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserService;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	 private JavaMailSender emailSender;

	@Override
	public List<Reservation> listAllReservations() {
		return reservationRepository.findAll();
	}

	@Override
	public void reserveBook(Long bookId, String email) {
		User user = userRepository.findByEmail(email);
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
		Reservation reservation = new Reservation();
		reservation.setId_User(user);
		reservation.setId_Book(book);
		reservation.setStatus("pending");
		reservation.setReservation(Date.valueOf(LocalDate.now()));
		reservationRepository.save(reservation);
	}

	@Override
	public void cancelReservation(Long reservationId, String email) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

		if (!reservation.getId_User().getEmail().equals(email)) {
			throw new IllegalArgumentException("You cannot cancel someone else's reservation.");
		}

		reservationRepository.delete(reservation);
	}

	@Override
	public List<Reservation> findReservationsByUser(User user) {

		List<Reservation> resers = listAllReservations();
		List<Reservation> userRes = new ArrayList<>();

		for (Reservation re : resers) {
			if (user == re.getId_User()) {
				userRes.add(re);
			}

		}

		return userRes;
	}

	@Override
	public List<Reservation> findReservationsBetween(Date fromDate, Date toDate) {
		return reservationRepository.findByReservationBetween(fromDate, toDate);

	}

	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}
	
	// Enviar correo de confirmación
	public void sendEmail(Long bookId, String email) {
		User user = userRepository.findByEmail(email);
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
		SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(user.getEmail());
	    message.setSubject("Reserva de libro creada");
	    message.setText("Has reservado el libro " + book.getTitle());
	    emailSender.send(message);
	}

}
