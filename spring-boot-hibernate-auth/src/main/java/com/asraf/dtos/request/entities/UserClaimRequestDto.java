package com.asraf.dtos.request.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserClaimRequestDto extends BaseRequestDto {

	@NotNull
	private Long userId;

	@NotBlank
	@Size(min = 3, max = 100)
	private String claimType;

	@NotBlank
	@Size(min = 3, max = 100)
	private String claimValue;

}
