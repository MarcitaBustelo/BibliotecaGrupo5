package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;

@Repository("reservationRepository")
public interface ReservationRepository extends JpaRepository<Reservation, Serializable> {

	List<Reservation> findByBook(Book book);

	List<Reservation> findByUser(User user);

}
