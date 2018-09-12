package com.asraf.dtos.response.entities;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserResponseDto extends BaseEntityResponseDto {
	private String username;
	private String email;
	private List<UserVerificationResponseDto> userVerifications;
}
