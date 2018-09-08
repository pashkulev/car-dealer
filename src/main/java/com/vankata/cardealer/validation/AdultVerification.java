package com.vankata.cardealer.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AdultVerificationValidator.class)
public @interface AdultVerification {

    String message() default "A customer should be adult (above 18 years old)!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
