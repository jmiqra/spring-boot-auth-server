package com.asraf.dtos.request.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OauthClientDetailsRequestDto extends BaseRequestDto {

	@NotBlank
	private String clientId;

	private String resourceIds;

	private String clientSecret;

	private String scope;

	private String authorizedGrantTypes;

	private String webServerRedirectUri;

	private String authorities;

	private Integer accessTokenValidity;

	private Integer refreshTokenValidity;

	@Size(min = 3, max = 4096)
	private String additionalInformation;

	private String autoapprove;

}
