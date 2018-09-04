package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.UserDetailsUpdateRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;

public interface UserDetailsUpdateMapper
		extends RequestResponseDtoMapper<User, UserResponseDto, UserDetailsUpdateRequestDto> {

}
