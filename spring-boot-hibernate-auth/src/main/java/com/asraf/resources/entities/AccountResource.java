package com.asraf.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.http.HttpMethod;

import com.asraf.controllers.AccountController;
import com.asraf.controllers.UserController;
import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

import lombok.Getter;

@Getter
public class AccountResource extends BaseResource {

	private final UserResponseDto user;

	public AccountResource(final User user, final UserMapper userMapper) {

		this.user = userMapper.getResponseDto(user);
		// final long id = user.getId();

		add(new ExtendedLink(linkTo(AccountController.class).withRel("accounts")).withMethod(HttpMethod.GET));
		
		this.loadCommonLink();
	}

	public AccountResource forDeletion() {
		super.removeLinks();
		this.loadCommonLink();
		return this;
	}

	private void loadCommonLink() {
		add(new ExtendedLink(linkTo(UserController.class).withRel("users")).withMethod(HttpMethod.GET)
				.withSearchableData());
	}
}