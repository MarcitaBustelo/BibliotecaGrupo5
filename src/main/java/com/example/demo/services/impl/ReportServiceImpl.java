package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	public List<Map<String, Object>> getMostBorrowedBooks() {
		List<Object[]> results = loanRepository.findMostBorrowedBooks();

		List<Map<String, Object>> booksWithBorrowCounts = new ArrayList<>();
		for (Object[] result : results) {
			Book book = (Book) result[0];
			Long borrowCount = (Long) result[1];

			Map<String, Object> bookData = new HashMap<>();
			bookData.put("title", book.getTitle());
			bookData.put("author", book.getAuthor());
			bookData.put("borrowCount", borrowCount);

			booksWithBorrowCounts.add(bookData);
		}
		return booksWithBorrowCounts;
	}

	public long getTotalUserCount() {
		return userRepository.countAllUsers();
	}

	@Override
	public int countLoansByUser(String email) {
		User user = userRepository.findByEmail(email);
		return loanRepository.countByUser(user.getEmail());

	}

	@Override
	public List<Loan> getUserLoanHistory(Long userId) {
		return loanRepository.findLoansByUserId(userId);

	}

	public List<Loan> getBookLoanHistory(Long bookId) {
		return loanRepository.findByBookId(bookId); 
	}

	public long getTotalBookCount() {
		return bookRepository.count(); 
	}
	
	public List<Loan> getBookLoanHistoryByUser(Long bookId, Long userId) {
	    return loanRepository.findByBookIdAndUserId(bookId, userId);
	}

	public List<User> getUsersByBookId(Long bookId) {
	    return loanRepository.findUsersByBookId(bookId);
	}


}
