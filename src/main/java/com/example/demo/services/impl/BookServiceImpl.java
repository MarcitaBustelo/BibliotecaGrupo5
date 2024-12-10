package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.converter.BookConverter;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.models.BookModel;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;

@Service("libroService")
public class BookServiceImpl implements BookService {

	@Autowired
	@Qualifier("libroRepository")
	private BookRepository libroRepository;

	@Autowired
	@Qualifier("userRepository")
	private BookRepository userRepository;

	@Autowired
	@Qualifier("libroConverter")
	private BookConverter libroConverter;

	@Override
	public List<BookModel> listAllBooks() {
		List<BookModel> libros = new ArrayList<BookModel>();
		for (Book libro : libroRepository.findAll())
			libros.add(libroConverter.entity2model(libro));
		return libros;

	}

	@Override
	public Book addBook(BookModel libro) {
		Book lib = libroConverter.model2entity(libro);
		return libroRepository.save(lib);
	}

	@Override
	public int removeBook(int id) {
		libroRepository.deleteById(id);
		return 0;
	}

	@Override
	public Book updateBook(BookModel libroModel) {
		Book libro = libroConverter.model2entity(libroModel);
		return libroRepository.save(libro);
	}

	@Override
	public Book findById(long id) {
		Book book = libroRepository.findById(id);
		return book;
	}

}