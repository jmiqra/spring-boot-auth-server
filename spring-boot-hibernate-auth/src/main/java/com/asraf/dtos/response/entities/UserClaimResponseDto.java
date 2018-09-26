package com.asraf.dtos.response.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserClaimResponseDto extends BaseEntityResponseDto {

	private UserResponseDto user;
	private String claimType;
	private String claimValue;

}
