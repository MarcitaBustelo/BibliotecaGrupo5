package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/", "/books/**", "/register/**", "/about/**", "/photos/**")
						.permitAll().requestMatchers("/users", "/admin/**").hasRole("ADMIN")
						.requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/vendor/**",
								"/fonts/**", "/webjars/**", "/photos/**", "/changePassword")
						.permitAll().anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").usernameParameter("email").defaultSuccessUrl("/", true)
						.permitAll())
				.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}