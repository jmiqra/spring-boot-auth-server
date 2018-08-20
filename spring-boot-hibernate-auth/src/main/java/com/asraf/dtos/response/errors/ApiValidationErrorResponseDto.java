package com.asraf.dtos.response.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
public class ApiValidationErrorResponseDto extends ApiSubErrorResponseDto {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	public ApiValidationErrorResponseDto(String object, String message) {
		this.object = object;
		this.message = message;
	}
}