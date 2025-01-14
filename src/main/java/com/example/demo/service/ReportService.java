package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;

@Service
public interface ReportService {

	List<Map<String, Object>> getMostBorrowedBooks();

	List<Reservation> getUserLoanHistory(Long userId);

	long getTotalUserCount();

}
