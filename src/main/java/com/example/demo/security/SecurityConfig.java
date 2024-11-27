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
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/", "/register/**", "/login", "/webjars/**").permitAll()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .defaultSuccessUrl("/", true)  
	            .permitAll()
	            .successHandler((request, response, authentication) -> {  
	                String role = authentication.getAuthorities().stream()
	                        .map(grantedAuthority -> grantedAuthority.getAuthority())
	                        .findFirst()
	                        .orElse("ROLE_USER");

	                if ("ROLE_ADMIN".equals(role)) {  
	                    response.sendRedirect("/admin");
	                } else {
	                    response.sendRedirect("/user"); 
	                }
	            })
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/welcome") 
	            .permitAll()
	        );

	    return http.build();
	}


}