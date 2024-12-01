package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;

public interface BookService {

	List<BookModel> listAllBooks();
	Book addBook(BookModel books);
	int removeBook(int id);
	Book updateCourse(BookModel book);
}
