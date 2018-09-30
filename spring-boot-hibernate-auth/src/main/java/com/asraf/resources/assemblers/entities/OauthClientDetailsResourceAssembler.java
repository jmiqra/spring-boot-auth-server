package com.asraf.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.OauthClientDetailsController;
import com.asraf.dtos.mapper.OauthClientDetailsMapper;
import com.asraf.entities.OauthClientDetails;
import com.asraf.resources.assemblers.BaseResourceAssembler;
import com.asraf.resources.entities.OauthClientDetailsResource;

@Component
public class OauthClientDetailsResourceAssembler extends BaseResourceAssembler<OauthClientDetails, OauthClientDetailsResource> {

	private final OauthClientDetailsMapper oauthClientDetailsMapper;

	@Autowired
	public OauthClientDetailsResourceAssembler(OauthClientDetailsMapper oauthClientDetailsMapper) {
		super(OauthClientDetailsController.class, OauthClientDetailsResource.class);
		this.oauthClientDetailsMapper = oauthClientDetailsMapper;
	}

	@Override
	public OauthClientDetailsResource toResource(OauthClientDetails entity) {
		return new OauthClientDetailsResource(entity, oauthClientDetailsMapper);
	}

}