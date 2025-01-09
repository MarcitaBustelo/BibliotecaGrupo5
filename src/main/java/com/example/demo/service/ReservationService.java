package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

	void reserveBook(Long bookId, String email);
    void cancelReservation(Long reservationId, String email);
}
