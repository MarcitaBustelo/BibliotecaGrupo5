package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;
import com.example.demo.entity.User;

@Service
public interface ReportService {

	List<Map<String, Object>> getMostBorrowedBooks();

	List<Loan> getUserLoanHistory(Long userId);

	long getTotalUserCount();

	int countLoansByUser(String email);

	long getTotalBookCount();

	List<Loan> getBookLoanHistory(Long bookId);

	List<Loan> getBookLoanHistoryByUser(Long bookId, Long userId);

	List<User> getUsersByBookId(Long bookId);

}
