package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class RestUser {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}/activate")
	public ResponseEntity<Map<String, Object>> activateUser(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		if (userService.setUserActivation(id, true)) {
			response.put("success", true);
			response.put("message", "User with ID " + id + " has been activated successfully.");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "User with ID " + id + " not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}/deactivate")
	public ResponseEntity<Map<String, Object>> deactivateUser(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		if (userService.setUserActivation(id, false)) {
			response.put("success", true);
			response.put("message", "User with ID " + id + " has been deactivated successfully.");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "User with ID " + id + " not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}
