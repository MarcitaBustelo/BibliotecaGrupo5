package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;
import com.example.demo.entity.User;

@Service
public interface LoanService {

	void loanBook(Long bookId, String username);

	void returnBook(Long bookId, String username);

	List<Loan> findLoansByUser(User user);

	List<Loan> listAllLoans();

	void deleteLoan(Long id);

	int countLoansByUser(String username);

	List<Object[]> getLoansByMonth();

	List<Object[]> getLoansPerUser();

}
