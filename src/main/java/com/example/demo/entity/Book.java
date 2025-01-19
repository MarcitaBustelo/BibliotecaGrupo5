package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String title;

	private String image;
	private String author;
	private String genre;
	private LocalDate yearPublished;
	private boolean isAvailable = true;

	@OneToMany(mappedBy = "book")
	private List<Reservation> reservations;

	@OneToMany(mappedBy = "book")
	private List<Loan> loans;

	public Book(long id, String title, String image, String author, String genre, LocalDate yearPublished,
			boolean isAvailable, List<Reservation> reservations, List<Loan> loans) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.author = author;
		this.genre = genre;
		this.yearPublished = yearPublished;
		this.isAvailable = isAvailable;
		this.reservations = reservations;
		this.loans = loans;
	}

	public Book() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public LocalDate getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(LocalDate yearPublished) {
		this.yearPublished = yearPublished;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void isAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", image=" + image + ", author=" + author + ", genre=" + genre
				+ ", yearPublished=" + yearPublished + ", isAvailable=" + isAvailable + ", reservations=" + reservations
				+ ", loans=" + loans + "]";
	}

}
