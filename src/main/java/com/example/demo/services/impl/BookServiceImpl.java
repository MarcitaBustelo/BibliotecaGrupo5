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
	public List<BookModel> listAllBooks() {
		List<BookModel> libros = new ArrayList<BookModel>();
		for (Book libro : bookRepository.findAll())
			libros.add(bookConverter.entity2model(libro));
		return libros;

	}

	@Override
	public Book addBook(BookModel libro) {
		Book lib = bookConverter.model2entity(libro);
		return bookRepository.save(lib);
	}

	@Override
	public int removeBook(int id) {
		bookRepository.deleteById(id);
		return 0;
	}

	@Override
	public Book updateBook(BookModel libroModel) {
		Book libro = bookConverter.model2entity(libroModel);
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