package com.vankata.cardealer.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DiscountVerificationValidator implements ConstraintValidator<DiscountVerification, Integer> {
    @Override
    public boolean isValid(Integer discount, ConstraintValidatorContext constraintValidatorContext) {
        return discount >= 0 && discount <= 100;
    }
}
