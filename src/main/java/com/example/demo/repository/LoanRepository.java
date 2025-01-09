package com.example.demo.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;

@Repository("loanRepository")
public interface LoanRepository extends JpaRepository<Loan, Serializable>{

	int countByUser(User user);
    Optional<Loan> findByUserAndBook(User user, Book book);

}
