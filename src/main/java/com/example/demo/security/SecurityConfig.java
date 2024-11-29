package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/registerForm", "/register", "/login", "/webjars/**").permitAll().anyRequest().authenticated() // Proteger todas las demás rutas
				).formLogin(form -> form.loginPage("/login") // Página personalizada de inicio de sesión
						.permitAll() // Permitir acceso a todos para la página de login
				).logout(logout -> logout.permitAll() // Permitir cierre de sesión para todos
				);

		return http.build();
	}

}