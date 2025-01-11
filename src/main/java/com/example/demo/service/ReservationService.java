package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;

@Service
public interface ReservationService {

	void reserveBook(Long bookId, String email);

	void cancelReservation(Long reservationId, String email);

	List<Reservation> findReservationsByUser(User user);

	List<Reservation> listAllReservations();

	List<Reservation> findReservationsBetween(Date fromDate, Date toDate);

	void deleteReservation(Long id);

}
