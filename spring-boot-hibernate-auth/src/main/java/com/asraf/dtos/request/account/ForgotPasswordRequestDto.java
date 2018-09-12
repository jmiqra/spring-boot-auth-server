package com.asraf.dtos.request.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ForgotPasswordRequestDto extends BaseRequestDto {

	@NotBlank
	@Size(min = 3, max = 100)
	private String username;

}
