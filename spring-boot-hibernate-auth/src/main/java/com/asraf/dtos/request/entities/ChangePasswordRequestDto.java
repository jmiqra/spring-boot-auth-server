package com.asraf.dtos.request.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;
import com.asraf.validators.IsEqualConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@IsEqualConstraint.List({
	@IsEqualConstraint(baseField = "password", dependentField = "confirmPassword", message = "password didn't match"),
	})
public class ChangePasswordRequestDto extends BaseRequestDto {

	@NotBlank
	@Size(min = 3, max = 100)
	private String password;

	@NotBlank
	@Size(min = 3, max = 100)
	private String confirmPassword;

}
