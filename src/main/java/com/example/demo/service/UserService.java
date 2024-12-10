package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.models.UserModel;

public interface UserService {

	User findById(int id);

	void register(UserModel userModel);

	void delete(int id);

	void edit(UserModel userModel);

	List<User> getAllUsers();

	List<UserModel> getAllUsersModel();

}
