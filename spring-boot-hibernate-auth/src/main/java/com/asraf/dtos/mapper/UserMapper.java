package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.User;

public interface UserMapper extends RequestResponseDtoMapper<User, UserResponseDto, UserRequestDto> {

}
