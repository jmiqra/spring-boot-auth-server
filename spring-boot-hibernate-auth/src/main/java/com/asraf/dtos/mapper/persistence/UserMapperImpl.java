package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserClaimResponseDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.dtos.response.entities.UserVerificationResponseDto;
import com.asraf.entities.User;
import com.asraf.entities.UserClaim;
import com.asraf.entities.UserVerification;

@Component
@Scope(value = "prototype")
@Primary
public class UserMapperImpl extends RequestResponseDtoMapperImpl<User, UserResponseDto, UserRequestDto>
		implements UserMapper {

	@Autowired
	public UserMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, UserResponseDto.class, User.class);

		PropertyMap<UserVerification, UserVerificationResponseDto> userVerificationEntityToResponsePropertyMap = new PropertyMap<UserVerification, UserVerificationResponseDto>() {
			protected void configure() {
				skip().setUser(null);
			}
		};

		PropertyMap<UserClaim, UserClaimResponseDto> userClaimEntityToResponsePropertyMap = new PropertyMap<UserClaim, UserClaimResponseDto>() {
			protected void configure() {
				skip().setUser(null);
			}
		};

		super.setNestedObjectPropertyMap(userVerificationEntityToResponsePropertyMap)
				.setNestedObjectPropertyMap(userClaimEntityToResponsePropertyMap);

	}

}
