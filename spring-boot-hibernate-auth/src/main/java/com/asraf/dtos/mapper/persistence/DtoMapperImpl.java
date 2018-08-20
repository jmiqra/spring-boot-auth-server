package com.asraf.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.asraf.dtos.mapper.DtoMapper;

public abstract class DtoMapperImpl implements DtoMapper {

	protected final ModelMapper modelMapper;

	protected DtoMapperImpl(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

}
