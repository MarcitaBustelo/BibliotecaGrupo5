package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private User user;
	private Book book;
	private Date initial_date;
	private Date due_date;

	public Loan(long id, User user, Book book, Date initial_date, Date due_date) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.initial_date = initial_date;
		this.due_date = due_date;
	}

	public Loan() {
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

	public Date getInitial_date() {
		return initial_date;
	}

	public void setInitial_date(Date initial_date) {
		this.initial_date = initial_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", user=" + user + ", book=" + book + ", initial_date=" + initial_date + ", due_date="
				+ due_date + "]";
	}

}
