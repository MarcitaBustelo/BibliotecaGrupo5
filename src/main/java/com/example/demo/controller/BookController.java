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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.models.BookModel;
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
	public ModelAndView listBooks(Authentication authentication) {
		ModelAndView mav = new ModelAndView(BOOKS_VIEW);
		List<Book> books = bookService.listAllBooks();
		mav.addObject("books", (books != null) ? books : new ArrayList<BookModel>());

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

		model.addAttribute("books", book);
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
			existingBook.setYearPublished(updatedBook.getYearPublished());

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

}
