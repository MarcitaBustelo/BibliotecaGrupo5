package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Date reservation;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="id_user", nullable = false)
	private User id_user;
 
	@ManyToOne
	@JoinColumn(name="id_book", nullable = false)
	private Book id_book;

	public Reservation(long id, User id_user, Book id_book, Date reservation, String status) {
		super();
		this.id = id;
		this.id_user = id_user;
		this.id_book = id_book;
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

	public User getId_User() {
		return id_user;
	}

	public void setId_User(User id_user) {
		this.id_user = id_user;
	}

	public Book getId_Book() {
		return id_book;
	}

	public void setId_Book(Book id_book) {
		this.id_book = id_book;
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
		return "Reservation [id=" + id + ", id_user=" + id_user + ", id_book=" + id_book + ", reservation=" + reservation
				+ ", status=" + status + "]";
	}

}
