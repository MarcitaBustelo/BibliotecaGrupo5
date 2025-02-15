package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Date initial_date;
	private Date due_date;

	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_book", nullable = false)
	private Book book;

	private boolean deleted = false;

	public Loan(long id, User user, Book book, Date initial_date, Date due_date, boolean deleted) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.initial_date = initial_date;
		this.due_date = due_date;
		this.deleted = deleted;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", initial_date=" + initial_date + ", due_date=" + due_date + ", user=" + user
				+ ", book=" + book + "]";
	}

}
