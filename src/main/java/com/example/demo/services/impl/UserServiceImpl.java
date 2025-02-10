package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userRepository")
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(User user) {

		User existingUser = userRepository.findByEmail(user.getEmail());

		if (existingUser != null) {
			throw new RuntimeException("Email already in use");
		}

		System.out.println("Saving user: " + user);

		return userRepository.save(user);
	}

	public boolean setUserActivation(Long id, boolean activate) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setActivated(activate);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll().stream().collect(Collectors.toList());
	}

}