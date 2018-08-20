package com.asraf.dtos.response.requestdto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class FieldValidations {
	private Boolean isRequired;
	private Boolean isEmail;
	private Boolean isMobileNumber;
	private Long minValue;
	private Long maxValue;
	private Integer minSize;
	private Integer maxSize;
	private String pattern;
	private Map<String, Object> options;
}
