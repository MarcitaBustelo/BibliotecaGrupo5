package com.example.demo.models;

import java.time.LocalDate;

public class BookModel {

	private long id;
	private String title, image, author, genre;
	private LocalDate yearPublished;
	private boolean isAvailable;
	
	public BookModel(long id, String title, String image, String author, String genre, LocalDate yearPublished,
			boolean isAvaliable) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.author = author;
		this.genre = genre;
		this.yearPublished = yearPublished;
		this.isAvailable = isAvaliable;
	}

	public BookModel() {
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

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "BookModel [id=" + id + ", title=" + title + ", image=" + image + ", author=" + author + ", genre="
				+ genre + ", yearPublished=" + yearPublished + "]";
	}

}
