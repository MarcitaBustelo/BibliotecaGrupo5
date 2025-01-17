package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/books/**", "/register/**", "/about/**", "/photos/**").permitAll()
				.requestMatchers("/users", "/admin/**").hasRole("ADMIN")
				.requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**",
						"/webjars/**", "/photos/**", "/changePassword")
				.permitAll().anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").usernameParameter("email")
						.failureHandler(customAuthenticationFailureHandler()) 
						.defaultSuccessUrl("/login?success=Login successful!", true)
						.permitAll())
				.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return (request, response, exception) -> {
			if (exception instanceof DisabledException) {
				response.sendRedirect("/login?error=Your account is not activated.");
			} else if (exception instanceof BadCredentialsException) {
				response.sendRedirect("/login?email=Invalid username or password.");
			} else {
				response.sendRedirect("/login?error=Login failed. Please try again.");
			}
		};
	}
}
