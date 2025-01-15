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
public interface LoanRepository extends JpaRepository<Loan, Serializable>{

	int countByUser(User user);
    Optional<Loan> findByUserAndBook(User user, Book book);
   
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user.email = :email")
    int countByUser(@Param("email") String username);
    
    @Query("SELECT MONTH(l.initial_date) AS month, COUNT(l) AS loanCount FROM Loan l GROUP BY MONTH(l.initial_date)")
    List<Object[]> findLoansByMonth();

    @Query("SELECT l.user, COUNT(l) FROM Loan l GROUP BY l.user")
    List<Object[]> findLoansPerUser();
}
