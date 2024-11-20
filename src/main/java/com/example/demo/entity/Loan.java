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
	@JoinColumn(name="id_user", nullable = false)
	private User id_user;
 
	@ManyToOne
	@JoinColumn(name="id_book",nullable = false)
	private Book id_book;
	
	public Loan(long id, User id_user, Book id_book, Date initial_date, Date due_date) {
		super();
		this.id = id;
		this.id_user = id_user;
		this.id_book = id_book;
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
		return "Loan [id=" + id + ", user=" + id_user + ", book=" + id_book + ", initial_date=" + initial_date + ", due_date="
				+ due_date + "]";
	}

}
