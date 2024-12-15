package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.storage.StorageService;

@SpringBootApplication
public class BibliotecaGrupo5Application {

	public static void main(String[] args) {

		SpringApplication.run(BibliotecaGrupo5Application.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> storageService.init();
	}

}
