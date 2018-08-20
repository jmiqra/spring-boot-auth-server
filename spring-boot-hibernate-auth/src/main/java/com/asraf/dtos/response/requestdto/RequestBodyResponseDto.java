package com.asraf.dtos.response.requestdto;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.asraf.dtos.request.BaseRequestDto;
import com.asraf.dtos.response.BaseResponseDto;
import com.asraf.utils.EnumUtils;
import com.asraf.validators.ContactNumberConstraint;
import com.asraf.validators.EnumValueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestBodyResponseDto<T extends BaseRequestDto> extends BaseResponseDto {

	@Getter
	@Setter
	private List<RequestField> fields = new ArrayList<>();

	private Class<T> clazzRequestDto;

	public RequestBodyResponseDto(Class<T> clazzRequestDto) {
		this.clazzRequestDto = clazzRequestDto;
		for (Field field : this.clazzRequestDto.getDeclaredFields()) {
			fields.add(getRequestField(field));
		}
	}

	public RequestBodyResponseDto<T> withSuperClass(int level) {
		Class<?> clazzCurrent = this.clazzRequestDto;
		for (; level > 0; level--) {
			Class<?> clazzSuper = clazzCurrent.getSuperclass();
			if (clazzSuper == null) {
				return this;
			}
			for (Field field : clazzSuper.getDeclaredFields()) {
				fields.add(getRequestField(field));
			}
			clazzCurrent = clazzSuper;
		}
		return this;
	}

	private RequestField getRequestField(Field field) {
		FieldValidations validations = new FieldValidations();
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof NotNull || annotation instanceof NotBlank || annotation instanceof NotEmpty) {
				validations.setIsRequired(true);
			} else if (annotation instanceof Email) {
				validations.setIsEmail(true);
			} else if (annotation instanceof ContactNumberConstraint) {
				validations.setIsMobileNumber(true);
			} else if (annotation instanceof Min) {
				validations.setMinValue(((Min) annotation).value());
			} else if (annotation instanceof Max) {
				validations.setMaxValue(((Max) annotation).value());
			} else if (annotation instanceof Size) {
				validations.setMinSize(((Size) annotation).min());
				validations.setMaxSize(((Size) annotation).max());
			} else if (annotation instanceof Pattern) {
				validations.setPattern(((Pattern) annotation).regexp());
			} else if (annotation instanceof EnumValueConstraint) {
				Class<? extends Enum<?>> enumClazz = ((EnumValueConstraint) annotation).enumClass();
				validations.setOptions(EnumUtils.getNameValues(enumClazz));
			}
		}
		return RequestField.builder().name(field.getName()).type(field.getType().getSimpleName())
				.parameterizedType(getParameterizedTypeName(field)).validations(validations).build();
	}

	private String getParameterizedTypeName(Field field) {
		try {
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			Class<?> parameterizedTypeClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
			return parameterizedTypeClass.getSimpleName();
		} catch (ClassCastException e) {
			return null;
		}
	}
}
