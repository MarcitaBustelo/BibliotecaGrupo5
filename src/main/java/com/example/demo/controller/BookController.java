package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;
import com.example.demo.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

	private static final String BOOKS_VIEW = "books";
	private static final String BOOKS_FORM = "bookForm";

	@Autowired
	private BookService bookService;

	@GetMapping("/listBooks")
	public ModelAndView listBooks(Authentication authentication) {
		ModelAndView mav = new ModelAndView(BOOKS_VIEW);
		List<BookModel> books = bookService.listAllBooks();
		mav.addObject("books", (books != null) ? books : new ArrayList<BookModel>());
		return mav;
	}
	
	@GetMapping("/deleteBook")
	public String deleteCourse(@RequestParam("id") int id) {
		bookService.removeBook(id);
		return "redirect:/admin/bookADMIN";
	}

	@GetMapping("/updateBook")
	public String showEditLibroForm(@RequestParam("id") int id, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("books", book);
		return BOOKS_FORM;
	}
	
	@GetMapping("/bookForm")
	public String formBook(Model model) {
		model.addAttribute("books", new BookModel());
		return BOOKS_FORM;
	}

	@PostMapping("/addBook")
	public String addBook(@ModelAttribute("books") BookModel bookModel, RedirectAttributes flash) {
	    bookService.addBook(bookModel);
	    flash.addFlashAttribute("success", "Book has been added");
		return "redirect:/admin/bookADMIN";
	}

}
