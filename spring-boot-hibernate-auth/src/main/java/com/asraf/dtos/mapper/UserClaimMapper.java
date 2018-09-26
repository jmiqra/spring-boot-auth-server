package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.UserClaimRequestDto;
import com.asraf.dtos.response.entities.UserClaimResponseDto;
import com.asraf.entities.UserClaim;

public interface UserClaimMapper
		extends RequestResponseDtoMapper<UserClaim, UserClaimResponseDto, UserClaimRequestDto> {

}
