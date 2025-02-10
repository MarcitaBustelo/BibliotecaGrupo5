package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {

	User findByEmail(String email);

	User registerUser(User user);

	boolean setUserActivation(Long id, boolean status);

	List<User> getAllUsers();

}
