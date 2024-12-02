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
		http.csrf().disable() 
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/registerForm", "/register", "/listBooks", "/about", "/login",
								"/webjars/**")
						.permitAll() 
						.anyRequest().authenticated() 
				).formLogin(form -> form.loginPage("/login") 
						.defaultSuccessUrl("/", true) 
						.permitAll() 
				).logout(logout -> logout.logoutUrl("/logout") 
						.logoutSuccessUrl("/") 
						.permitAll() 
				);

		return http.build();
	}

}