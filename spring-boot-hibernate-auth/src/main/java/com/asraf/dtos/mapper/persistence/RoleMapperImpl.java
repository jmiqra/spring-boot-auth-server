package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.RoleMapper;
import com.asraf.dtos.request.entities.RoleRequestDto;
import com.asraf.dtos.response.entities.RoleResponseDto;
import com.asraf.entities.Role;

@Component
@Scope(value = "prototype")
public class RoleMapperImpl extends RequestResponseDtoMapperImpl<Role, RoleResponseDto, RoleRequestDto>
		implements RoleMapper {

	@Autowired
	public RoleMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, RoleResponseDto.class, Role.class);

		// PropertyMap<RoleVerification, RoleVerificationResponseDto>
		// userVerificationEntityToResponsePropertyMap = new
		// PropertyMap<RoleVerification, RoleVerificationResponseDto>() {
		// protected void configure() {
		// skip().setRole(null);
		// }
		// };
		//
		// super.setNestedObjectPropertyMap(userVerificationEntityToResponsePropertyMap);

	}

}
