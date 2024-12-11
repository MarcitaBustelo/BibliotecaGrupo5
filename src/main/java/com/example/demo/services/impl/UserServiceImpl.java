package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.demo.entity.User;
import com.example.demo.models.UserModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	@Qualifier("userRepository")
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);

		UserBuilder builder = null;

		if (user != null) {
			builder = org.springframework.security.core.userdetails.User.withUsername(email);
			builder.authorities(new SimpleGrantedAuthority(user.getRole()));
			builder.password(user.getPassword());
		} else {
			throw new UsernameNotFoundException("Email not found");
		}

		return builder.build();
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void register(UserModel userModel) {
		userModel.setRole("ROLE_USER");
		userModel.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));

		userRepository.save(toUserEntity(userModel));
	}

	@Override
	public void delete(int id) {
		User user = userRepository.findById(id).orElse(null);

		if (user != null)
			userRepository.delete(user);
	}

	@Override
	public void edit(User user) {
		User u = userRepository.findById(user.getId()).orElse(null);

		if (user != null) {
			user.setEmail(user.getEmail());
			userRepository.save(u);
		}
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<UserModel> getAllUsersModel() {
		List<UserModel> users = new ArrayList<>();

		for (User u : userRepository.findAll())
			users.add(toUserModel(u));

		return users;
	}

	public void validateRegister(UserModel userModel, BindingResult bResult) {
		if (!userModel.getPassword().equals(userModel.getRePassword()))
			bResult.rejectValue("rePassword", "error.rePassword", "The passwords doesn't match");

		if (userRepository.findByEmail(userModel.getEmail()) != null)
			bResult.rejectValue("email", "error.email", "That email is already taken");
	}

	// MAPPERS
	public UserModel toUserModel(User user) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(user, UserModel.class);
	}

	public User toUserEntity(UserModel userModel) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(userModel, User.class);
	}
}