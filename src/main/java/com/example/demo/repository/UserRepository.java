package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.models.UserModel;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable> {

	User findByEmail(String email);

}