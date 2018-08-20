package com.asraf.dtos.response.entities;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserResponseDto extends BaseEntityResponseDto {
	private String email;
	private String name;
	// TODO: unable to show usreProfile in response because user entity has not userProfile
	// private UserProfileResponseDto userProfile;
	private List<UserVerificationResponseDto> userVerifications;
}
