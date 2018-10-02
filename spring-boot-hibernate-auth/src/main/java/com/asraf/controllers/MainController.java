package com.asraf.controllers;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.constants.RolePreAuthorizeConditions;
import com.asraf.resources.main.MainAnonymousResource;
import com.asraf.resources.main.MainAuthenticatedResource;

@RestController
@RequestMapping("")
public class MainController {

	@GetMapping("")
	public MainAnonymousResource getAnonymousLinks() throws UnsupportedEncodingException, MessagingException {
		return new MainAnonymousResource();
	}

	@GetMapping("/authenticated-links")
	public MainAuthenticatedResource getAuthenticatedLinks() {
		return new MainAuthenticatedResource();
	}

	@GetMapping("/{id}")
	public String getById(@PathVariable("id") String id) {
		return "getById -> " + id;
	}

	@PostMapping("")
	public String create() {
		return "create";
	}

	@PutMapping("")
	public String update() {
		return "update";
	}

	@PreAuthorize(RolePreAuthorizeConditions.ADMIN)
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") String id) {
		return "delete -> " + id;
	}
}
