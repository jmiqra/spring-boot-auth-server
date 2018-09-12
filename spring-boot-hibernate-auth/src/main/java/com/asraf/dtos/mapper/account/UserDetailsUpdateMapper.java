package com.asraf.dtos.mapper.account;

import com.asraf.dtos.mapper.RequestResponseDtoMapper;
import com.asraf.dtos.request.account.UserDetailsUpdateRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;

public interface UserDetailsUpdateMapper
		extends RequestResponseDtoMapper<User, UserResponseDto, UserDetailsUpdateRequestDto> {

}
