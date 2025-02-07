package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {


	@Qualifier("bookRepository")
	private BookRepository bookRepository;
	
    public List<Book> getAvailableBooks() {
        return bookRepository.findByIsAvailable(true);
    }
    
	@Override
	public List<Book> listAllBooks() {
		return bookRepository.findAll().stream().collect(Collectors.toList());


	}

	@Override
	public Book findById(long id) {
		Book book = bookRepository.findById(id);
		return book;
	}

}