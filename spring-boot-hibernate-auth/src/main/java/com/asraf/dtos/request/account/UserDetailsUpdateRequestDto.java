package com.asraf.dtos.request.account;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDetailsUpdateRequestDto extends BaseRequestDto {

	@NotBlank
	@Size(min = 3, max = 100)
	private String username;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 3, max = 45)
	private String userStatus;

	private Date lastLogin;

	private Integer wrongPasswordAttempt;

	private Date lastWrongPasswordAttempt;

	private List<Long> userVerificationIds;

	private List<Long> roleIds;

}
