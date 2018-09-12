package com.asraf.dtos.mapper;

import com.asraf.dtos.request.entities.OauthClientDetailsRequestDto;
import com.asraf.dtos.response.entities.OauthClientDetailsResponseDto;
import com.asraf.entities.OauthClientDetails;

public interface OauthClientDetailsMapper extends
		RequestResponseDtoMapper<OauthClientDetails, OauthClientDetailsResponseDto, OauthClientDetailsRequestDto> {

}
