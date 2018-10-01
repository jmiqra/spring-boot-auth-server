package com.asraf.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.UserController;
import com.asraf.dtos.mapper.UserMapper;
import com.asraf.entities.User;
import com.asraf.resources.assemblers.BaseResourceAssembler;
import com.asraf.resources.entities.UserResource;

@Component
public class UserResourceAssembler extends BaseResourceAssembler<User, UserResource> {

	private final UserMapper userMapper;

	@Autowired
	public UserResourceAssembler(UserMapper userMapper) {
		super(UserController.class, UserResource.class);
		this.userMapper = userMapper;
	}

	@Override
	public UserResource toResource(User entity) {
		return new UserResource(entity, userMapper);
	}

}