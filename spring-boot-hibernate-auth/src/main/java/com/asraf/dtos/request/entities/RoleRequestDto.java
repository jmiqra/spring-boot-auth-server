package com.asraf.dtos.request.entities;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoleRequestDto extends BaseRequestDto {

	@NotBlank
	@Size(min = 3, max = 45)
	private String name;

	private List<Long> userIds;

}
