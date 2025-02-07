package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookRest {

	@Autowired
	private BookService bookService;

	@GetMapping("/")
	public List<Book> getBook() {
		return bookService.listAllBooks();
	}

	@GetMapping("/available")
	public ResponseEntity<List<Book>> getAvailableBooks() {
		List<Book> availableBooks = bookService.getAvailableBooks();
		if (availableBooks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(availableBooks);
	}
}