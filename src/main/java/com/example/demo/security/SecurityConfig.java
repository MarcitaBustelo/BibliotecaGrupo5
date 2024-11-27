package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/register/**", "/login", "/webjars/**")
				.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()).formLogin()
				.loginPage("/login").defaultSuccessUrl("/admin", true).permitAll().and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/welcome") // Redirige a /welcome
												// tras el logout
				.permitAll();

		return http.build();
	}
}