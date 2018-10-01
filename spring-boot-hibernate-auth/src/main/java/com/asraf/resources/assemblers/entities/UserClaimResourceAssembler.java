package com.asraf.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.UserClaimController;
import com.asraf.dtos.mapper.UserClaimMapper;
import com.asraf.entities.UserClaim;
import com.asraf.resources.assemblers.BaseResourceAssembler;
import com.asraf.resources.entities.UserClaimResource;

@Component
public class UserClaimResourceAssembler extends BaseResourceAssembler<UserClaim, UserClaimResource> {

	private final UserClaimMapper userClaimMapper;

	@Autowired
	public UserClaimResourceAssembler(UserClaimMapper userClaimMapper) {
		super(UserClaimController.class, UserClaimResource.class);
		this.userClaimMapper = userClaimMapper;
	}

	@Override
	public UserClaimResource toResource(UserClaim entity) {
		return new UserClaimResource(entity, userClaimMapper);
	}

}