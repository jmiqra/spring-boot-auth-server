package com.asraf.dtos.response.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserVerificationResponseDto extends BaseEntityResponseDto {

	private String verificationCode;

	private Date creationTime;

	private UserResponseDto User;
	
}
