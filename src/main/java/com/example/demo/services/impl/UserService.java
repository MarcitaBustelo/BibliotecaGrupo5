package com.example.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	public com.example.demo.entity.User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.example.demo.entity.User usuario = userRepository.findByEmail(email);

		UserBuilder builder = null;

		if (usuario != null) {
			builder = User.withUsername(email);
			builder.disabled(false);
			builder.password(usuario.getPassword());
			builder.authorities(new SimpleGrantedAuthority(usuario.getRole()));

		} else
			throw new UsernameNotFoundException("Usuario no encontrado");

		return builder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public com.example.demo.entity.User registrar(com.example.demo.entity.User user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		user.setRole("user");
		return userRepository.save(user);

	}
}