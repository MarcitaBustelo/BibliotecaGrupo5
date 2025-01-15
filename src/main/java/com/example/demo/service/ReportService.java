package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;

@Service
public interface ReportService {

	List<Map<String, Object>> getMostBorrowedBooks();

	List<Loan> getUserLoanHistory(Long userId);

	long getTotalUserCount();

	int countLoansByUser(String email);

}
