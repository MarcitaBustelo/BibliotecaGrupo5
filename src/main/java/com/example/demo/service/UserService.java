package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.models.UserModel;

public interface UserService {

	User findById(int id);

	User findByEmail(String email);

	void register(UserModel userModel);

	void delete(int id);

	void edit(User user);

	List<User> getAllUsers();

	List<UserModel> getAllUsersModel();

	long getUserCount();

}
