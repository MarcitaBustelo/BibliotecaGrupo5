package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;

@Service
public interface BookService {

	List<Book> listAllBooks();

	Book addBook(Book books);

	int removeBook(int id);

	Book updateBook(Book book);

	Book findById(long id);

	Page<Book> getBooksPaginated(Pageable pageable);

	Page<Book> searchBooksByTitle(String title, Pageable pageable);

	Page<Book> getBooksOrderedAlphabetically(Pageable pageable);

	Page<Book> getBooksOrderedByDate(Pageable pageable);
}
