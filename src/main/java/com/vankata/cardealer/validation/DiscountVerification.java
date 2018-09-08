package com.vankata.cardealer.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DiscountVerificationValidator.class)
public @interface DiscountVerification {

    String message() default "Discount value should be in range 0-100!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
