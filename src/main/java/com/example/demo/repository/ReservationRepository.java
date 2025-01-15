package com.example.demo.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;

@Repository("reservationRepository")
public interface ReservationRepository extends JpaRepository<Reservation, Serializable> {

	List<Reservation> findByBook(Book book);

	List<Reservation> findByUser(User user);

	List<Reservation> findByUserId(Long userId);

	List<Reservation> findByReservationBetween(Date fromDate, Date toDate);

	@Query("SELECT r.book, COUNT(r) AS borrowCount " + "FROM Reservation r " + "GROUP BY r.book "
			+ "ORDER BY borrowCount DESC")
	List<Object[]> findMostBorrowedBooks();

	@Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
	List<Reservation> findReservationsByUserId(@Param("userId") Long userId);

	@Query("SELECT r FROM Reservation r WHERE r.book.id = :bookId ORDER BY r.reservation ASC")
	List<Reservation> findById_BookOrderByReservationAsc(@Param("bookId") Long bookId);
}
