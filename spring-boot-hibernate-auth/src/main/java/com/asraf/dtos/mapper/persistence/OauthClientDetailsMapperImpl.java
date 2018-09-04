package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.OauthClientDetailsMapper;
import com.asraf.dtos.request.entities.OauthClientDetailsRequestDto;
import com.asraf.dtos.response.entities.OauthClientDetailsResponseDto;
import com.asraf.entities.OauthClientDetails;

@Component
@Scope(value = "prototype")
public class OauthClientDetailsMapperImpl extends
		RequestResponseDtoMapperImpl<OauthClientDetails, OauthClientDetailsResponseDto, OauthClientDetailsRequestDto>
		implements OauthClientDetailsMapper {

	@Autowired
	public OauthClientDetailsMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, OauthClientDetailsResponseDto.class, OauthClientDetails.class);

	}

}
