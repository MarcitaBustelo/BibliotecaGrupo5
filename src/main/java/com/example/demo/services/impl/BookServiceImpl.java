package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.converter.BookConverter;
import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	@Qualifier("bookRepository")
	private BookRepository bookRepository;

	@Autowired
	@Qualifier("bookConverter")
	private BookConverter bookConverter;

	@Override
	public List<Book> listAllBooks() {
		List<Book> libros = new ArrayList<Book>();
		for (Book libro : bookRepository.findAll())
			libros.add(libro);
		return libros;

	}

	@Override
	public Book addBook(Book libro) {
		Book lib = libro;
		return bookRepository.save(lib);
	}

	@Override
	public int removeBook(int id) {
		bookRepository.deleteById(id);
		return 0;
	}

	@Override
	public Book updateBook(Book libroModel) {
		Book libro = libroModel;
		return bookRepository.save(libro);
	}

	@Override
	public Book findById(long id) {
		Book book = bookRepository.findById(id);
		return book;
	}

	public Page<Book> getBooksPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size); // PageRequest usa base 0
		return bookRepository.findAll(pageable);
	}

}