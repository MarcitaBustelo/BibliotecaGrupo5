package com.example.demo.services.impl;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void loanBook(Long bookId, String email) {
        User user = userRepository.findByEmail(email);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (loanRepository.countByUser(user) >= 5) {
            throw new IllegalArgumentException("You cannot loan more than 5 books.");
        }

        if (!book.isAvailable()) {
            throw new IllegalArgumentException("The book is not available.");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setInitial_date(Date.valueOf(LocalDate.now()));
        loan.setDue_date(Date.valueOf(LocalDate.now().plusWeeks(2)));

        book.isAvailable(false);

        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long bookId, String email) {
        User user = userRepository.findByEmail(email);
        Loan loan = loanRepository.findByUserAndBook(user, bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found")))
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        Book book = loan.getBook();
        book.isAvailable(true);

        loanRepository.delete(loan);
        bookRepository.save(book);
    }
}
