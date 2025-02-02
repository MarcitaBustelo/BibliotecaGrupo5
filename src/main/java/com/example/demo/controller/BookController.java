package com.example.demo.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.storage.StorageService;

@Controller
@RequestMapping("/books")
public class BookController {

	private static final String BOOKS_VIEW = "books";
	private static final String BOOKS_FORM = "bookForm";

	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;

	@GetMapping("/listBooks")
	public ModelAndView listBooks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "all") String filter) {

		ModelAndView mav = new ModelAndView(BOOKS_VIEW);

		int pageSize = 3;
		Pageable pageable = PageRequest.of(page, pageSize);

		Page<Book> booksPage;
		if (!search.isEmpty()) {
			booksPage = bookService.searchBooksByTitle(search, pageable);
		} else if (filter.equals("alphabetical")) {
			booksPage = bookService.getBooksOrderedAlphabetically(pageable);
		} else if (filter.equals("date")) {
			booksPage = bookService.getBooksOrderedByDate(pageable);
		} else {
			booksPage = bookService.getBooksPaginated(pageable);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
		List<Map<String, String>> formattedBooks = new ArrayList<>();

		for (Book book : booksPage.getContent()) {
			Map<String, String> formattedBook = new HashMap<>();
			formattedBook.put("image", book.getImage());
			formattedBook.put("title", book.getTitle());
			formattedBook.put("author", book.getAuthor());
			formattedBook.put("genre", book.getGenre());

			if (book.getYearPublished() != null) {
				formattedBook.put("yearPublished", book.getYearPublished().format(formatter));
			} else {
				formattedBook.put("yearPublished", "none");
			}

			formattedBook.put("isAvailable", book.isAvailable() ? "true" : "false");

			formattedBook.put("id", String.valueOf(book.getId()));
			formattedBooks.add(formattedBook);
		}

		mav.addObject("books", formattedBooks);
		mav.addObject("currentPage", page + 1);
		mav.addObject("totalPages", booksPage.getTotalPages());
		mav.addObject("totalItems", booksPage.getTotalElements());
		mav.addObject("search", search);
		mav.addObject("filter", filter);

		return mav;
	}

	@GetMapping("/deleteBook")
	public String deleteCourse(@RequestParam("id") int id) {
		Book book = bookService.findById(id);
		if (book != null) {
			try {
				storageService.delete(book.getImage());
			} catch (Exception e) {
				System.err.println("Error al eliminar la imagen: " + e.getMessage());
			}
			bookService.removeBook(id);
		}
		return "redirect:/admin/bookADMIN";
	}

	@GetMapping("/bookForm")
	public String showBookForm(@RequestParam(value = "id", required = false) Long id, Model model) {
		Book book;

		if (id != null) {
			book = bookService.findById(id);
		} else {
			book = new Book();
		}

		List<String> genres = Arrays.asList("Fiction", "Non-Fiction", "Mystery", "Science Fiction", "Fantasy",
				"Biography", "Romance", "Murder");

		if (book.getYearPublished() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = book.getYearPublished().format(formatter);
			model.addAttribute("formattedYearPublished", formattedDate);
		}

		model.addAttribute("books", book);
		model.addAttribute("genres", genres);
		return BOOKS_FORM;
	}

	@PostMapping("/addBook")
	public String addBook(@ModelAttribute("books") Book book, @RequestParam("file") MultipartFile file,
			RedirectAttributes flash) {
		if (book.getId() == 0) {
			if (!file.isEmpty()) {
				try {
					String filename = storageService.store(file, book.getId());
					book.setImage(filename); // Guarda solo el nombre del archivo en la base de datos
				} catch (Exception e) {
					flash.addFlashAttribute("error", "Error saving image file.");
					return "redirect:/admin/bookADMIN";
				}
			}
			bookService.addBook(book);
			flash.addFlashAttribute("success", "Book has been successfully added!");
			return "redirect:/admin/bookADMIN";
		} else {
			flash.addFlashAttribute("error", "An error occurred while adding the book.");
			return "redirect:/admin/bookADMIN";
		}
	}

	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute("books") Book updatedBook, @RequestParam("file") MultipartFile file,
			RedirectAttributes flash) {
		try {
			Book existingBook = bookService.findById(updatedBook.getId());
			if (existingBook == null) {
				flash.addFlashAttribute("error", "Book not found.");
				return "redirect:/admin/bookADMIN";
			}

			existingBook.setTitle(updatedBook.getTitle());
			existingBook.setAuthor(updatedBook.getAuthor());
			existingBook.setGenre(updatedBook.getGenre());

			if (updatedBook.getYearPublished() != null) {
				existingBook.setYearPublished(updatedBook.getYearPublished());
			}

			if (!file.isEmpty()) {
				try {
					String filename = storageService.store(file, updatedBook.getId());
					existingBook.setImage(filename);
				} catch (Exception e) {
					flash.addFlashAttribute("error", "Error updating image file.");
					return "redirect:/admin/bookADMIN";
				}
			}

			bookService.updateBook(existingBook);

			flash.addFlashAttribute("success", "Book has been successfully updated!");
		} catch (Exception e) {
			flash.addFlashAttribute("error", "An error occurred while updating the book.");
		}

		return "redirect:/admin/bookADMIN";
	}

	@GetMapping("/byCategory")
	public String getByCategoryGraphic(Model model) {

		List<Object[]> booksByCategoryData = bookService.getBooksByCategory();

		model.addAttribute("booksByCategory", booksByCategoryData);

		return "byCategory";
	}

}
