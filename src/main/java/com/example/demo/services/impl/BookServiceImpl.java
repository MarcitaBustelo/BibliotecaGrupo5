package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.BookConverter;
import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;

@Service("bookService")
public class BookServiceImpl  implements BookService {

	@Autowired
	@Qualifier("bookRepository")
	private BookRepository bookRepository;

	@Autowired
	@Qualifier("bookConverter")
	private BookConverter bookConverter;
	
	@Override
	public List<BookModel> listAllBooks() {
		List<BookModel> books = new ArrayList<BookModel>();
		for (Book book : bookRepository.findAll())
			books.add(bookConverter.entity2model(book));
		return books;

	}

	@Override
	public Book addBook(BookModel books) {
		Book book = bookConverter.model2entity(books);
		return bookRepository.save(book);
	}

	@Override
	public int removeBook(int id) {
		bookRepository.deleteById(id);
		return 0;
	}

	@Override
	public Book updateCourse(BookModel bookModel) {
		Book book = bookConverter.model2entity(bookModel);
		return bookRepository.save(book);
	}

}
