package com.example.demo.controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final String SECRET_KEY = "mySecretKeymySecretKeymySecretKeymySecretKey";
	private final long EXPIRATION_TIME = 86400000;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginRequest) {
		Optional<User> userOp = userService.findByEmail(loginRequest.getEmail());

		if (userOp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
		}

		User user = userOp.get();

		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
		}

		String token = getJWTToken(user.getEmail());
		user.setToken(token);

		return ResponseEntity.ok(user);
	}

	@PostMapping("/register")
	public User registerJWT(@RequestBody User user) {
		return userService.registerUser(user);
	}

	public String getJWTToken(String user) {
		return Jwts.builder().setSubject(user).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256).compact();
	}
}
