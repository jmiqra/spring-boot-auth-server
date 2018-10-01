package com.asraf.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import com.asraf.controllers.AccountController;
import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

import lombok.Getter;

@Getter
public class AccountResource extends BaseResource {

	private final UserResponseDto user;

	public AccountResource(final User user, final UserMapper userMapper)
			throws UnsupportedEncodingException, MessagingException {

		this.user = userMapper.getResponseDto(user);
		final long id = user.getId();

		add(new ExtendedLink(linkTo(methodOn(AccountController.class).createUser(null)).withSelfRel())
				.withMethod(HttpMethod.POST));
		add(new ExtendedLink(linkTo(methodOn(AccountController.class).updateUser(id, null)).withSelfRel())
				.withMethod(HttpMethod.PUT));
		add(new ExtendedLink(linkTo(methodOn(AccountController.class).forgotPassword(null)).withSelfRel())
				.withMethod(HttpMethod.POST));
		Link token = new Link("http://localhost:8081/oauth/token", "token");
		add(new ExtendedLink(token).withMethod(HttpMethod.GET));
		Link main = new Link("http://localhost:8081", "main");
		add(new ExtendedLink(main).withMethod(HttpMethod.GET));
	}

}