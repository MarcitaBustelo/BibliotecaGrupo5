package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class RestLoan {

    @Autowired
    private LoanService loanService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/loan/{bookId}")
    public ResponseEntity<String> loanBook(@PathVariable Long bookId, @RequestParam String email) {
        try {
            loanService.loanBook(bookId, email);
            return ResponseEntity.ok("Book loaned successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Devolver error específico
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @RequestParam String email) {
        try {
            loanService.returnBook(bookId, email);
            return ResponseEntity.ok("Book returned successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Devolver error específico
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}

