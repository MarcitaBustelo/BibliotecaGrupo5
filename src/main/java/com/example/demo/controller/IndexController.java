package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	private static final String INDEX = "welcome";
	private static final String ABOUT = "about";

	@GetMapping("/")
	public String index() {
		return INDEX;
	}

	@GetMapping("/about")
	public String about() {
		return ABOUT;
	}

}
