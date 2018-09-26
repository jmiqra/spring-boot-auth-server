package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.UserClaimMapper;
import com.asraf.dtos.request.entities.UserClaimRequestDto;
import com.asraf.dtos.response.entities.UserClaimResponseDto;
import com.asraf.entities.UserClaim;

@Component
@Scope(value = "prototype")
public class UserClaimMapperImpl extends
		RequestResponseDtoMapperImpl<UserClaim, UserClaimResponseDto, UserClaimRequestDto> implements UserClaimMapper {

	@Autowired
	public UserClaimMapperImpl(ModelMapper modelMapper) {
		super(modelMapper, UserClaimResponseDto.class, UserClaim.class);

		PropertyMap<UserClaimRequestDto, UserClaim> requestToEntityPropertyMap = new PropertyMap<UserClaimRequestDto, UserClaim>() {
			protected void configure() {
				map().getUser().setId(source.getUserId());
				map().getUser().setUserVerifications(null);
			}
		};

		super.setRequestToEntityPropertyMap(requestToEntityPropertyMap);

	}

	public void loadEntity(UserClaimRequestDto requestDto, UserClaim entity) {
		entity.setUser(null);
		super.loadEntity(requestDto, entity);
	}

}
