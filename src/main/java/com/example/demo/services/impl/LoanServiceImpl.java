package com.example.demo.services.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoanService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Override
	public void loanBook(Long bookId, String email) {
		User user = userRepository.findByEmail(email);
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));

		if (!book.isAvailable()) {
			throw new IllegalArgumentException("The book is not available.");
		}

		List<Loan> loans = findLoansByUser(user);
		long activeLoansCount = loans.stream().filter(loan -> !loan.isDeleted()).count();

		if (activeLoansCount >= 5) {
			throw new IllegalArgumentException("You cannot loan more than 5 books.");
		}

		Loan loan = new Loan();
		loan.setUser(user);
		loan.setBook(book);
		loan.setInitial_date(Date.valueOf(LocalDate.now()));
		loan.setDue_date(Date.valueOf(LocalDate.now().plusWeeks(2)));

		book.isAvailable(false);
		loanRepository.save(loan);
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

		if (loan.isPresent()) {
			Loan existingLoan = loan.get();
			existingLoan.setDeleted(true);
			loanRepository.save(existingLoan);

			Book book = existingLoan.getBook();
			book.isAvailable(true);
			bookRepository.save(book);

		} else {
			throw new EntityNotFoundException("Loan with id " + id + " not found");
		}

	}

	@Override
	public Optional<Loan> findById(Long id) {
		return loanRepository.findById(id);
	}

}
