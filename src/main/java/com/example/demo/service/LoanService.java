package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface LoanService {

	    void loanBook(Long bookId, String username);
	    void returnBook(Long bookId, String username);
	

}
