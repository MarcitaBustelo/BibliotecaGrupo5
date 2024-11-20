package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private User user;
	private Book book;
	private Date reservation;
	private String status;

	public Reservation(long id, User user, Book book, Date reservation, String status) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.reservation = reservation;
		this.status = status;
	}

	public Reservation() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getReservation() {
		return reservation;
	}

	public void setReservation(Date reservation) {
		this.reservation = reservation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", user=" + user + ", book=" + book + ", reservation=" + reservation
				+ ", status=" + status + "]";
	}

}
