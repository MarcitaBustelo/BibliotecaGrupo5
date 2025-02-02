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

	int countByUser(User user);

	Book findById(int id);

	Optional<Loan> findByUserAndBook(User user, Book book);

	@Query("SELECT COUNT(l) FROM Loan l WHERE l.user.email = :email")
	int countByUser(@Param("email") String email);


	@Query("SELECT MONTH(l.initial_date) AS month, COUNT(l) AS loanCount FROM Loan l GROUP BY MONTH(l.initial_date) ORDER BY MONTH(l.initial_date)")
	List<Object[]> findLoansByMonth();

	@Query("SELECT l.user, COUNT(l) FROM Loan l GROUP BY l.user.name")
	List<Object[]> findLoansPerUser();

	@Query("SELECT l.book, COUNT(l) AS borrowCount " + "FROM Loan l " + "GROUP BY l.book "
			+ "ORDER BY borrowCount DESC")
	List<Object[]> findMostBorrowedBooks();

	List<Loan> findLoansByUserId(Long userId);

	List<Loan> findByBookId(Long bookId);

	@Query("SELECT DISTINCT l.user FROM Loan l WHERE l.book.id = :bookId")
	List<User> findUsersByBookId(@Param("bookId") Long bookId);

	List<Loan> findByBookIdAndUserId(Long bookId, Long userId);

}
