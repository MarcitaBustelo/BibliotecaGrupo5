package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // 🔹 Desactiva CSRF (para APIs REST)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll() // 🔹 Permite login y
																								// registro sin
																								// autenticación
						.requestMatchers("/api/users/**", "/api/books/**").permitAll() // 🔹 Permite acceso público a
																						// usuarios
						.anyRequest().authenticated()) // 🔹 Protege el resto de las rutas
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // 🔹 No redirige
																										// a login
				.formLogin(form -> form.disable()) // 🔥 Desactiva formularios de login completamente
				.httpBasic().disable(); // 🔥 Desactiva autenticación básica

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
