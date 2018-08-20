package com.asraf.dtos.mapper.persistence;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.UserVerificationMapper;
import com.asraf.dtos.request.entities.UserVerificationRequestDto;
import com.asraf.dtos.response.entities.UserVerificationResponseDto;
import com.asraf.entities.UserVerification;

@Component
@Scope(value = "prototype")
public class UserVerificationMapperImpl
		extends RequestResponseDtoMapperImpl<UserVerification, UserVerificationResponseDto, UserVerificationRequestDto>
		implements UserVerificationMapper {

	@Autowired
	public UserVerificationMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, UserVerificationResponseDto.class, UserVerification.class);

		PropertyMap<UserVerificationRequestDto, UserVerification> requestToEntityPropertyMap = new PropertyMap<UserVerificationRequestDto, UserVerification>() {
			protected void configure() {
				map().getUser().setId(source.getUserId());
				// map().setCreationTime(new Date());
				// using(convertMassToLarge).map(source.getMass()).setLarge(false);
			}
		};

		PropertyMap<UserVerification, UserVerificationResponseDto> entityToResponsePropertyMap = new PropertyMap<UserVerification, UserVerificationResponseDto>() {
			protected void configure() {
				skip().getUser().setUserVerifications(null);
			}
		};

		super.setRequestToEntityPropertyMap(requestToEntityPropertyMap)
				.setEntityToResponsePropertyMap(entityToResponsePropertyMap);
	}

	public UserVerification getEntity(UserVerificationRequestDto requestDto) {
		UserVerification userVerification = super.getEntity(requestDto);
		userVerification.setCreationTime(new Date());
		return userVerification;
	}
	
	public void loadEntity(UserVerificationRequestDto requestDto, UserVerification entity) {
		entity.setUser(null);
		super.loadEntity(requestDto, entity);
	}
}
