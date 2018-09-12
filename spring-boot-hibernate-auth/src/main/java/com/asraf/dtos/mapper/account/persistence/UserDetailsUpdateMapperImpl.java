package com.asraf.dtos.mapper.account.persistence;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.account.UserDetailsUpdateMapper;
import com.asraf.dtos.mapper.persistence.RequestResponseDtoMapperImpl;
import com.asraf.dtos.request.account.UserDetailsUpdateRequestDto;
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
