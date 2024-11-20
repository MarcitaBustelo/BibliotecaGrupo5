package com.example.demo.models;

import java.sql.Date;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;

public class ReservationModel {

	private long id;
	private User user;
	private Book book;
	private Date reservation;
	private String status;

	public ReservationModel(long id, User user, Book book, Date reservation, String status) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.reservation = reservation;
		this.status = status;
	}

	public ReservationModel() {
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
		return "ReservationModel [id=" + id + ", user=" + user + ", book=" + book + ", reservation=" + reservation
				+ ", status=" + status + "]";
	}

}
