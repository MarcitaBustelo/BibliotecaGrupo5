package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;

@Service
public interface BookService {

	List<Book> listAllBooks();

	Book findById(long id);
	
	List<Book> getAvailableBooks();

}
