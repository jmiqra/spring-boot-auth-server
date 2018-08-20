package com.asraf.dtos.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
public class ListOfResponseDto<TResponseDto extends BaseResponseDto> extends BaseResponseDto {
	private List<TResponseDto> responseList;
}
