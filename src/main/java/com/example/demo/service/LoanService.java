package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;
import com.example.demo.entity.User;

@Service
public interface LoanService {

	void loanBook(Long bookId, String username);

	Optional<Loan> findById(Long id);

	List<Loan> findLoansByUser(User user);

	List<Loan> listAllLoans();

	void deleteLoan(Long idLoan);
}
