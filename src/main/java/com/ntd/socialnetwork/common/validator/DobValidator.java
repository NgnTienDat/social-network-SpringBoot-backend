package com.ntd.socialnetwork.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, Instant> {

    private int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Instant instant, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(instant)) return true;

        LocalDate dob = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        long years = ChronoUnit.YEARS.between(dob, now);

        return years >= min;
    }
}
