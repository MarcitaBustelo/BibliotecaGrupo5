package com.example.demo.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class CustomDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		User user = optionalUser.get();

		// Retornar usuario con roles (si tienes roles en la BD, agrégalos aquí)
		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
				.password(user.getPassword()) // La contraseña debería estar hasheada en la BD con BCrypt
				.roles(user.getRole()) // Si tu usuario tiene roles, agrégalo aquí
				.build();
	}
}
