package com.example.demo.converter;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;

@Component("bookConverter")
public class BookConverter {

	public BookModel entity2model(Book book) {
		BookModel bookModel = new BookModel();
		bookModel.setId(book.getId());
		bookModel.setTitle(book.getTitle());
		bookModel.setImage(book.getImage());
		bookModel.setAuthor(book.getAuthor());
		bookModel.setGenre(book.getGenre());
		bookModel.setYearPublished(book.getYearPublished());
		return bookModel;
	}

	public Book model2entity(BookModel bookModel) {
		Book book = new Book();
		book.setId(bookModel.getId());
		book.setTitle(bookModel.getTitle());
		book.setImage(bookModel.getImage());
		book.setAuthor(bookModel.getAuthor());
		book.setGenre(bookModel.getGenre());
		book.setYearPublished(bookModel.getYearPublished());
		return book;
	}

}
