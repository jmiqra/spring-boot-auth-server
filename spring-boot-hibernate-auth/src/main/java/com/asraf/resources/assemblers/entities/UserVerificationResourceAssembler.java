package com.asraf.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.UserVerificationController;
import com.asraf.dtos.mapper.UserVerificationMapper;
import com.asraf.entities.UserVerification;
import com.asraf.resources.assemblers.BaseResourceAssembler;
import com.asraf.resources.entities.UserVerificationResource;

@Component
public class UserVerificationResourceAssembler
		extends BaseResourceAssembler<UserVerification, UserVerificationResource> {

	private final UserVerificationMapper userVerificationMapper;

	@Autowired
	public UserVerificationResourceAssembler(UserVerificationMapper userVerificationMapper) {
		super(UserVerificationController.class, UserVerificationResource.class);
		this.userVerificationMapper = userVerificationMapper;
	}

	@Override
	public UserVerificationResource toResource(UserVerification entity) {
		return new UserVerificationResource(entity, userVerificationMapper);
	}

}