package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;

@Service
public interface BookService {

	List<BookModel> listAllBooks();

	Book addBook(BookModel books);

	int removeBook(int id);

	Book updateBook(BookModel book);

	Book findById(long id);

	Page<Book> getBooksPaginated(int page, int size);
}
