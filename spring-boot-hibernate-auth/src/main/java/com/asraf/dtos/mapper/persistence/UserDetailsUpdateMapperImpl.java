package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.UserDetailsUpdateMapper;
import com.asraf.dtos.request.entities.UserDetailsUpdateRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;

@Component
@Scope(value = "prototype")
public class UserDetailsUpdateMapperImpl
		extends RequestResponseDtoMapperImpl<User, UserResponseDto, UserDetailsUpdateRequestDto>
		implements UserDetailsUpdateMapper {

	@Autowired
	public UserDetailsUpdateMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, UserResponseDto.class, User.class);

	}

}
