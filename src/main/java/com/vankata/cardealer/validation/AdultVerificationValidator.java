package com.vankata.cardealer.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AdultVerificationValidator implements ConstraintValidator<AdultVerification, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return birthDate != null && birthDate.plusYears(18).isBefore(LocalDate.now());

    }
}
