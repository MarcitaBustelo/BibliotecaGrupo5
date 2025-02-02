package com.example.demo.services.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoanService;
import com.example.demo.service.ReservationService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void loanBook(Long bookId, String email) {
	    User user = userRepository.findByEmail(email);
	    Book book = bookRepository.findById(bookId)
	            .orElseThrow(() -> new IllegalArgumentException("Book not found"));

	    List<Loan> loans = listAllLoans();
		List<Loan> userLoans = new ArrayList<>();

		for (Loan lo : loans) {
			if (user == lo.getUser()) {
				userLoans.add(lo);
			}

		}
	    long activeLoansCount = userLoans.stream()
	            .filter(loan -> !loan.isDeleted())
	            .count();

	    if (activeLoansCount >= 5) {
	        throw new IllegalArgumentException("You cannot loan more than 5 books.");
	    }

	    if (!book.isAvailable()) {
	        throw new IllegalArgumentException("The book is not available.");
	    }

	    Loan loan = new Loan();
	    loan.setUser(user);
	    loan.setBook(book);
	    loan.setInitial_date(Date.valueOf(LocalDate.now()));
	    loan.setDue_date(Date.valueOf(LocalDate.now().plusWeeks(2)));

	    book.isAvailable(false);

	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(user.getEmail());
	    message.setSubject("You have loaned a book");
	    message.setText("You have borrowed '" + book.getTitle() + "' Hope you love it! Enjoy!");
	    emailSender.send(message);

	    loanRepository.save(loan);
	    bookRepository.save(book);
	}


	@Override
	public void returnBook(Long bookId, String email) {
		User user = userRepository.findByEmail(email);
		Loan loan = loanRepository
				.findByUserAndBook(user,
						bookRepository.findById(bookId)
								.orElseThrow(() -> new IllegalArgumentException("Book not found")))
				.orElseThrow(() -> new IllegalArgumentException("Loan not found"));

		Book book = loan.getBook();
		book.isAvailable(true);

		loanRepository.delete(loan);
		bookRepository.save(book);
	}

	@Override
	public List<Loan> findLoansByUser(User user) {
		List<Loan> loans = listAllLoans();
		List<Loan> userLoans = new ArrayList<>();

		for (Loan lo : loans) {
			if (user == lo.getUser()) {
				userLoans.add(lo);
			}

		}

		return userLoans;
	}

	@Override
	public List<Loan> listAllLoans() {
		List<Loan> loans = new ArrayList<>();
		for (Loan loan : loanRepository.findAll())
			loans.add(loan);
		return loans;
	}

	@Override
	public void deleteLoan(Long id) {
		Optional<Loan> loan = loanRepository.findById(id);
		User user = userRepository.findByEmail(loan.get().getUser().getEmail());

		if (loan.isPresent()) {
			Loan existingLoan = loan.get();
			existingLoan.setDeleted(true);
			loanRepository.save(existingLoan);

			Book book = existingLoan.getBook();
			book.isAvailable(true);
			bookRepository.save(book);

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(user.getEmail());
			message.setSubject("You have returned a book");
			message.setText("You have return '" + book.getTitle()
					+ "' to the library! Start loaning more books right away! Enjoy! http://localhost:8081/books/listBooks");
			emailSender.send(message);

			reservationService.handleLoanRemoval(book.getId());
		} else {
			throw new EntityNotFoundException("Loan with id " + id + " not found");
		}

	}

	@Override
	public int countLoansByUser(String email) {
		User user = userRepository.findByEmail(email);
		return loanRepository.countByUser(user.getEmail());

	}

	@Override
	public List<Object[]> getLoansByMonth() {
		return loanRepository.findLoansByMonth();
	}

	@Override
	public List<Object[]> getLoansPerUser() {
		return loanRepository.findLoansPerUser();

	}

}
