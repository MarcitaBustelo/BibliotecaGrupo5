package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.BookModel;
import com.example.demo.service.BookService;

@Controller
public class BookController {

	private static final String BOOKS_VIEW = "books";

	@Autowired
	private BookService bookService;

	@GetMapping("/listBooks")
	public ModelAndView listBooks(Authentication authentication) {
		ModelAndView mav = new ModelAndView(BOOKS_VIEW);

		// Agregar la lógica de autenticación
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			mav.addObject("authenticated", true);
			mav.addObject("username", authentication.getName());
		} else {
			mav.addObject("authenticated", false);
		}

		// Mantener la funcionalidad original
		List<BookModel> books = bookService.listAllBooks();
		mav.addObject("books", (books != null) ? books : new ArrayList<BookModel>());

		return mav;
	}

}
