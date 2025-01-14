package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Map<String, Object>> getMostBorrowedBooks() {
		List<Object[]> results = reservationRepository.findMostBorrowedBooks();

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

	public List<Reservation> getUserLoanHistory(Long userId) {
		return reservationRepository.findReservationsByUserId(userId);
	}

	public long getTotalUserCount() {
		return userRepository.countAllUsers();
	}
}
