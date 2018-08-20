package com.asraf.dtos.mapper.errors;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.asraf.dtos.response.errors.CsvValidationErrorResponseDto;

public interface CsvValidationErrorMapper {

	CsvValidationErrorResponseDto addValidationErrors(Set<ConstraintViolation<Object>> constraintViolations, int row);

}