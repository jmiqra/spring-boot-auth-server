package com.asraf.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IsEqualValidator.class})
@Documented
public @interface IsEqualConstraint {
 
    String message() default "Invalid matching";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
 
    String baseField();
 
    String dependentField();
    
    //String[] stringList();
    
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List
    {
    	IsEqualConstraint[] value();
    }
    
}