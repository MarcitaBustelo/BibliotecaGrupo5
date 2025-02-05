package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userRepository")
	UserRepository userRepository;


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


}