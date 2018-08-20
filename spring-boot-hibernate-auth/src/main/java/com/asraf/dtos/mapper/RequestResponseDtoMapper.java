package com.asraf.dtos.mapper;

import com.asraf.dtos.request.BaseRequestDto;
import com.asraf.dtos.response.entities.BaseEntityResponseDto;
import com.asraf.entities.BaseEntity;

public interface RequestResponseDtoMapper<TEntity extends BaseEntity, TResponseDto extends BaseEntityResponseDto, TRequestDto extends BaseRequestDto>
		extends ResponseDtoMapper<TEntity, TResponseDto> {

	TEntity getEntity(TRequestDto requestDto);

	void loadEntity(TRequestDto requestDto, TEntity entity);

}
