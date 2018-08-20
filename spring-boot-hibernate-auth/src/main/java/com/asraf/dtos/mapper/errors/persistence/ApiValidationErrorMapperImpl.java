package com.asraf.dtos.mapper.errors.persistence;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.asraf.dtos.mapper.errors.ApiValidationErrorMapper;
import com.asraf.dtos.mapper.persistence.DtoMapperImpl;
import com.asraf.dtos.response.errors.ApiValidationErrorResponseDto;

@Component
@Scope(value = "prototype")
public class ApiValidationErrorMapperImpl extends DtoMapperImpl implements ApiValidationErrorMapper {

	@Autowired
	protected ApiValidationErrorMapperImpl(ModelMapper modelMapper) {
		super(modelMapper);
	}

	public ApiValidationErrorResponseDto getApiValidationError(ConstraintViolation<?> constraintViolation) {
		return new ApiValidationErrorResponseDto(constraintViolation.getRootBeanClass().getSimpleName(),
				((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
				constraintViolation.getInvalidValue(), constraintViolation.getMessage());
	}

	public List<ApiValidationErrorResponseDto> getApiValidationErrors(
			Set<ConstraintViolation<?>> constraintViolations) {
		return constraintViolations.stream().map(this::getApiValidationError).collect(Collectors.toList());
	}

	public List<ApiValidationErrorResponseDto> getApiValidationErrorsOfType(
			Set<ConstraintViolation<Object>> constraintViolations) {
		return constraintViolations.stream().map(this::getApiValidationError).collect(Collectors.toList());
	}

	public ApiValidationErrorResponseDto getApiValidationError(FieldError fieldError) {
		return new ApiValidationErrorResponseDto(fieldError.getObjectName(), fieldError.getField(),
				fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}

	public ApiValidationErrorResponseDto getApiValidationError(ObjectError objectError) {
		return new ApiValidationErrorResponseDto(objectError.getObjectName(), objectError.getDefaultMessage());
	}

}
