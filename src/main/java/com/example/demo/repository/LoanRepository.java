package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;

@Repository("loanRepository")
public interface LoanRepository extends JpaRepository<Loan, Serializable> {

	Book findById(int id);

	int countByUserAndDeletedFalse(User user);

	Optional<Loan> findByUserAndBook(User user, Book book);

}
