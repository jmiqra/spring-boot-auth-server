package com.asraf.resources.entities;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpMethod;

import com.asraf.controllers.RoleController;
import com.asraf.controllers.UserController;
import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

import lombok.Getter;

@Getter
public class UserResource extends BaseResource {

	private final UserResponseDto user;

	public UserResource(final User user, final UserMapper userMapper) {

		this.user = userMapper.getResponseDto(user);
		final long id = user.getId();

		add(new ExtendedLink(linkTo(methodOn(UserController.class).getById(id)).withSelfRel())
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(methodOn(UserController.class).update(id, null)).withSelfRel())
				.withMethod(HttpMethod.PUT));
		add(new ExtendedLink(linkTo(methodOn(UserController.class).delete(id)).withSelfRel())
				.withMethod(HttpMethod.DELETE));
		add(new ExtendedLink(linkTo(UserController.class).withRel("users")).withMethod(HttpMethod.GET));

		this.loadCommonLink();

	}

	public UserResource forDeletion() {
		super.removeLinks();
		this.loadCommonLink();
		return this;
	}

	private void loadCommonLink() {
		add(new ExtendedLink(linkTo(methodOn(UserController.class).create(null)).withRel("create"))
				.withMethod(HttpMethod.POST));
		add(new ExtendedLink(linkTo(methodOn(UserController.class).getRequests()).withRel("requests"))
				.withMethod(HttpMethod.GET));
		add(new ExtendedLink(linkTo(UserController.class).withRel("users")).withMethod(HttpMethod.GET)
				.withSearchableData());
		add(new ExtendedLink(linkTo(methodOn(RoleController.class).getByQuery(null, null, null)).withRel("roles"))
				.withMethod(HttpMethod.GET).withSearchableData());
	}
}