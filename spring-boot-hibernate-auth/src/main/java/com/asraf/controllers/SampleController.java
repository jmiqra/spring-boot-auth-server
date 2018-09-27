package com.asraf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
	@GetMapping("/login")
	public String showForm() {
		return "sample";
	}

}