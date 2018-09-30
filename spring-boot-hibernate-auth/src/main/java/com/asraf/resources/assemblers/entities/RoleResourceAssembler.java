package com.asraf.resources.assemblers.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.RoleController;
import com.asraf.dtos.mapper.RoleMapper;
import com.asraf.entities.Role;
import com.asraf.resources.assemblers.BaseResourceAssembler;
import com.asraf.resources.entities.RoleResource;

@Component
public class RoleResourceAssembler extends BaseResourceAssembler<Role, RoleResource> {

	private final RoleMapper roleMapper;

	@Autowired
	public RoleResourceAssembler(RoleMapper roleMapper) {
		super(RoleController.class, RoleResource.class);
		this.roleMapper = roleMapper;
	}

	@Override
	public RoleResource toResource(Role entity) {
		return new RoleResource(entity, roleMapper);
	}

}