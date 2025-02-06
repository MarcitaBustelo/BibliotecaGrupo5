package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {

	User findByEmail(String email);

	User registerUser(String name, String lastname, String email, String password);

	boolean setUserActivation(Long id, boolean status);

	List<User> getAllUsers();

}
