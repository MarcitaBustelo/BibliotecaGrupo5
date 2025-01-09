package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Serializable> {

	Book findById(long id);
	
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findAllByOrderByTitleAsc(Pageable pageable);

    Page<Book> findAllByOrderByYearPublishedDesc(Pageable pageable);


}
