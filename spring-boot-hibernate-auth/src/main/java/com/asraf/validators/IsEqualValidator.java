package com.asraf.validators;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEqualValidator implements ConstraintValidator<IsEqualConstraint, Object> {

	private String baseField;
	private String dependentField;
	// private String[] stringList;

	@Override
	public void initialize(IsEqualConstraint constraint) {
		baseField = constraint.baseField();
		dependentField = constraint.dependentField();
		// stringList = constraint.stringList();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		try {
			Object baseFieldValue = getFieldValue(object, baseField);
			Object dependentFieldValue = getFieldValue(object, dependentField);
			boolean flag = false;
			if (baseFieldValue.equals(dependentFieldValue)) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	private Object getFieldValue(Object object, String fieldName) throws Exception {
		Class<?> clazz = object.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(object);
	}

}