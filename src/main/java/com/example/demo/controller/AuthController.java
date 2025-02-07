package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String password = request.get("password");
		String name = request.get("name");
		String lastname = request.get("lastname");

		// 🔹 Asegurar valores predeterminados sin pasarlos en el body
		String role = "ROLE_USER";
		boolean activated = false;

		// 🔹 Validación de campos vacíos
		if (email == null || password == null || name == null || lastname == null) {
			return ResponseEntity.badRequest().body(Map.of("success", false, "error", "Missing fields"));
		}

		try {

			User user = userService.registerUser(name, lastname, email, password, role, activated);

			return ResponseEntity
					.ok(Map.of("success", true, "message", "User registered successfully", "email", user.getEmail()));
		} catch (Exception e) {
			System.out.println("Error registering user: " + e.getMessage()); // Debugging
			return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
		}
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String password = request.get("password");

		if (email == null || password == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Missing email or password"));
		}

		User user = userService.findByEmail(email);

		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
		}

		return ResponseEntity.ok(Map.of("message", "Login successful", "email", email));
	}
}
