package com.asraf.dtos.request.main;

import javax.validation.constraints.NotBlank;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TokenRequestDto extends BaseRequestDto {
	@NotBlank
	private String grant_type;
	private String username;
	private String password;
	private String refresh_token;
}
