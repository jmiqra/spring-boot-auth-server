package com.asraf.dtos.request.entities;

import javax.validation.constraints.NotNull;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserProfileRequestDto extends BaseRequestDto {

	@NotNull
	private String address;

	@NotNull
	private String phoneNumber;

}
